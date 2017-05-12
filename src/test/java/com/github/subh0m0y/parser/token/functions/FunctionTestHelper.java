package com.github.subh0m0y.parser.token.functions;

import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.operands.Real;

import java.util.Random;

/**
 * @author Subhomoy Haldar
 * @version 2017.05.12
 */
class FunctionTestHelper {
    private static final double MINIMUM = -100;
    private static final double RANGE = 200;

    static final int RUNS = 1_000;
    static final double EPS = 1e-15;

    static Operand getOperand(final Random random) {
        return new Real(random.nextDouble() * RANGE + MINIMUM);
    }
}
