package com.github.subh0m0y.parser.token.functions;

import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;

/**
 * Wrapper for the Sine function (sin).
 *
 * @author Subhomoy Haldar
 * @version 2017.04.20
 */
public class Sine extends Operator {
    public static final Sine INSTANCE = new Sine();

    private Sine() {
        super("sin", 1, FUNCTIONAL);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].sine();
    }
}
