package com.github.subh0m0y.parser.token.operations;

import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;
import com.github.subh0m0y.parser.exceptions.ArityException;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class Division extends Operator {
    public static final Division INSTANCE = new Division();

    private Division() {
        super("/", 2, MULTIPLICATIVE);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].divide(operands[1]);
    }
}
