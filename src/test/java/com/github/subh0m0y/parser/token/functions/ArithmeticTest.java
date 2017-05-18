package com.github.subh0m0y.parser.token.functions;

import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.VariableMap;
import com.github.subh0m0y.parser.token.operands.Variable;
import com.github.subh0m0y.parser.token.operations.Assignment;
import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.assertEquals;

/**
 * @author Subhomoy Haldar
 * @version 2017.05.18
 */
public class ArithmeticTest {
    private final Random random = new Random();

    @Test
    public void testAddition() throws Exception {
        for (int i = 0; i < FunctionTestHelper.RUNS; i++) {
            Operand operandA = FunctionTestHelper.getOperand(random);
            Operand operandB = FunctionTestHelper.getOperand(random);
            double valueA = operandA.getValue();
            double valueB = operandB.getValue();
            double expected = valueA + valueB;
            double actual = operandA.add(operandB).getValue();
            assertEquals(actual, expected, FunctionTestHelper.EPS);
        }
    }

    @Test
    public void testMultiplication() throws Exception {
        for (int i = 0; i < FunctionTestHelper.RUNS; i++) {
            Operand operandA = FunctionTestHelper.getOperand(random);
            Operand operandB = FunctionTestHelper.getOperand(random);
            double valueA = operandA.getValue();
            double valueB = operandB.getValue();
            double expected = valueA * valueB;
            double actual = operandA.multiply(operandB).getValue();
            assertEquals(actual, expected, FunctionTestHelper.EPS);
        }
    }

    @Test
    public void testExponentiation() throws Exception {
        for (int i = 0; i < FunctionTestHelper.RUNS; i++) {
            Operand operandA = FunctionTestHelper.getOperand(random).abs();
            Operand operandB = FunctionTestHelper.getOperand(random);
            double valueA = operandA.getValue();
            double valueB = operandB.getValue();
            double expected = Math.pow(valueA, valueB);
            double actual = operandA.pow(operandB).getValue();
            assertEquals(actual, expected, FunctionTestHelper.EPS);
        }
    }

    @Test
    public void testDivision() throws Exception {
        for (int i = 0; i < FunctionTestHelper.RUNS; i++) {
            Operand operandA = FunctionTestHelper.getOperand(random);
            Operand operandB = FunctionTestHelper.getOperand(random);
            double valueA = operandA.getValue();
            double valueB = operandB.getValue();
            double expected = valueA / valueB;
            double actual = operandA.divide(operandB).getValue();
            assertEquals(actual, expected, FunctionTestHelper.EPS);
        }
    }

    @Test
    public void testAssignment() throws Exception {
        for (int i = 0; i < FunctionTestHelper.RUNS; i++) {
            Variable x = new Variable(randomName(random));
            Operand operand = FunctionTestHelper.getOperand(random);
            double expected = operand.getValue();
            double actual = Assignment.INSTANCE.evaluate(x, operand).getValue();
            assertEquals(actual, expected, FunctionTestHelper.EPS);
            assertEquals(VariableMap.INSTANCE.get(x).getValue(), expected, FunctionTestHelper.EPS);
        }
    }

    private static final int MAX_LENGTH = 1024;

    private static String randomName(final Random random) {
        final int length = random.nextInt(MAX_LENGTH);
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(randomLetter(random));
        }
        return builder.toString();
    }

    private static String randomLetter(final Random random) {
        int offset = random.nextInt(26);
        return String.valueOf('a' + offset);
    }
}
