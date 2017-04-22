package com.github.subh0m0y.parser.token.functions;

import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;

/**
 * Wrapper for the Tangent function (tan).
 *
 * @author Subhomoy Haldar
 * @version 2017.04.20
 */
public class Tangent extends Operator {
    public static final Tangent INSTANCE = new Tangent();

    private Tangent() {
        super("tan", 1, FUNCTIONAL);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].tangent();
    }
}
