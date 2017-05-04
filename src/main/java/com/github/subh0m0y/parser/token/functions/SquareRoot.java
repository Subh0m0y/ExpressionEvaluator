package com.github.subh0m0y.parser.token.functions;

import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.exceptions.EvaluationException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;

/**
 * Wrapper for the square root function (sqrt).
 *
 * @author Subhomoy Haldar
 * @version 2017.04.22
 */
public class SquareRoot extends Operator {
    public static final SquareRoot INSTANCE = new SquareRoot();

    private SquareRoot() {
        super("sqrt", 1, FUNCTIONAL);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException, EvaluationException {
        check(operands.length);
        return operands[0].sqrt();
    }
}
