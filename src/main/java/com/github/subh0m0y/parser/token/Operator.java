package com.github.subh0m0y.parser.token;

import com.github.subh0m0y.parser.ExpressionEvaluator;
import com.github.subh0m0y.parser.ExpressionEvaluator.EvaluationException;
import com.github.subh0m0y.parser.exceptions.ArityException;

import java.util.StringJoiner;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public abstract class Operator implements Token {
    protected static final int ASSIGNMENT = 0;
    protected static final int ADDITIVE = 1;
    protected static final int MULTIPLICATIVE = 2;
    protected static final int EXPONENTIAL = 3;
    protected static final int FUNCTIONAL = 4;

    private final int arity;
    private final int priority;
    private final String symbol;

    protected Operator(final String symbol, final int arity, final int priority) {
        this.symbol = symbol;
        this.arity = arity;
        this.priority = priority;
    }

    protected void check(int length) throws ArityException {
        if (length != arity) {
            throw new ArityException(this, arity, length);
        }
    }

    public abstract Operand evaluate(final Operand... operands)
            throws ArityException, EvaluationException;

    private String formatAsPrefix(final Operand... operands) {
        StringJoiner joiner = new StringJoiner(" ", "(", ")");
        joiner.add(symbol);
        for (Operand operand : operands) {
            joiner.add(operand.toString());
        }
        return joiner.toString();
    }

    public String formatWith(final Operand... operands)
            throws ArityException {
        check(operands.length);
        if (arity == 2) {
            return operands[0] + " " + symbol + " " + operands[1];
        }
        return formatAsPrefix(operands);
    }

    public boolean isFunction() {
        return priority == FUNCTIONAL;
    }

    public int getArity() {
        return arity;
    }

    public int getPriority() {
        return priority;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
