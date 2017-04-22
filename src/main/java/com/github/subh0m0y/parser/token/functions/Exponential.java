package com.github.subh0m0y.parser.token.functions;

import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;

/**
 * Wrapper for the Exponential function (exp).
 *
 * @author Subhomoy Haldar
 * @version 2017.04.20
 */
public class Exponential extends Operator {
    public static final Exponential INSTANCE = new Exponential();

    private Exponential() {
        super("exp", 1, FUNCTIONAL);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].exp();
    }
}
