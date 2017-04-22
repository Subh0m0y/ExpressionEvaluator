package com.github.subh0m0y.parser;

import com.github.subh0m0y.parser.token.operands.Real;
import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.*;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class RealTest {
    private static final int COUNT = 100;
    private final Random random = new Random();

    @Test
    public void testGetValue() throws Exception {
        for (int i = 0; i < COUNT; i++) {
            double value = random.nextDouble();
            Real operand = new Real(value);
            assertEquals(operand.getValue(), value);
        }
    }

    @Test
    public void testToString() throws Exception {
        for (int i = 0; i < COUNT; i++) {
            double value = random.nextDouble();
            Real operand = new Real(value);
            assertEquals(operand.toString(), String.format("%.2f", value));
        }
    }

}