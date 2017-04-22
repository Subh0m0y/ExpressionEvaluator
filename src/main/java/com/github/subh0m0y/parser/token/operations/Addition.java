package com.github.subh0m0y.parser.token.operations;

import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;
import com.github.subh0m0y.parser.exceptions.ArityException;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class Addition extends Operator {
    public static final Addition INSTANCE = new Addition();

    private Addition() {
        super("+", 2, ADDITIVE);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].add(operands[1]);
    }
}
