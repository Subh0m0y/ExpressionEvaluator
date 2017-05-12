package com.github.subh0m0y.parser.token.functions;

import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;

/**
 * @author Subhomoy Haldar
 * @version 2017.05.12
 */
public class Logarithm extends Operator {
    public static final Logarithm INSTANCE = new Logarithm();

    private Logarithm() {
        super("log", 1, FUNCTIONAL);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].log();
    }
}
