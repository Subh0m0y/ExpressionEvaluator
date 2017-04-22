package com.github.subh0m0y.parser.token.operations;

import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;
import com.github.subh0m0y.parser.exceptions.ArityException;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class RaisingToPower extends Operator {
    public static final RaisingToPower INSTANCE = new RaisingToPower();

    private RaisingToPower() {
        super("^", 2, EXPONENTIAL);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].pow(operands[1]);
    }
}
