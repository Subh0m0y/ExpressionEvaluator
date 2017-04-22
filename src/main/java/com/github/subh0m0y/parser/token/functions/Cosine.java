package com.github.subh0m0y.parser.token.functions;

import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;

/**
 * Wrapper for the Cosine function (cos).
 *
 * @author Subhomoy Haldar
 * @version 2017.04.20
 */
public class Cosine extends Operator {
    public static final Cosine INSTANCE = new Cosine();

    private Cosine() {
        super("cos", 1, FUNCTIONAL);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].cosine();
    }
}
