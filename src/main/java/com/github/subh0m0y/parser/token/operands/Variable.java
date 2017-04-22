package com.github.subh0m0y.parser.token.operands;

import com.github.subh0m0y.parser.ExpressionEvaluator;
import com.github.subh0m0y.parser.ExpressionEvaluator.EvaluationException;
import com.github.subh0m0y.parser.token.Operand;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class Variable implements Operand {
    private final String symbol;

    public Variable(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public double getValue() throws EvaluationException {
        if (!isInitialized()) {
            throw new EvaluationException("Variable " + symbol + " is not initialized.");
        }
        return VariableMap.INSTANCE.get(this).getValue();
    }

    @Override
    public Operand add(Operand addend) {
        return new Real(getValue()).add(addend);
    }

    @Override
    public Operand multiply(Operand multiplicand) {
        return new Real(getValue()).multiply(multiplicand);
    }

    @Override
    public Operand divide(Operand divisor) {
        return new Real(getValue()).divide(divisor);
    }

    @Override
    public Operand pow(Operand exponent) {
        return new Real(getValue()).pow(exponent);
    }

    @Override
    public Operand sine() {
        return new Real(getValue()).sine();
    }

    @Override
    public Operand cosine() {
        return new Real(getValue()).cosine();
    }

    @Override
    public Operand tangent() {
        return new Real(getValue()).tangent();
    }

    @Override
    public Operand exp() {
        return new Real(getValue()).exp();
    }

    private boolean isInitialized() {
        return VariableMap.INSTANCE.get(this) != null;
    }

    @Override
    public String toString() {
        return symbol + (isInitialized()
                ? " = " + String.format("%.2f", getValue())
                : "");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && symbol.equals(((Variable) obj).symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
