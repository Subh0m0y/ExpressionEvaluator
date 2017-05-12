package com.github.subh0m0y.parser.token.functions;

import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.exceptions.EvaluationException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;

/**
 * Wrapper for the absolute value function (abs).
 *
 * @author Subhomoy Haldar
 * @version 2017.05.12
 */
public class Absolute extends Operator {
    public static final Absolute INSTANCE = new Absolute();

    private Absolute() {
        super("abs", 1, FUNCTIONAL);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException, EvaluationException {
        check(operands.length);
        return operands[0].abs();
    }
}
