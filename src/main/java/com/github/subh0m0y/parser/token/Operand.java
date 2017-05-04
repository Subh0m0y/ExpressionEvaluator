package com.github.subh0m0y.parser.token;

/**
 * This class defines the high-level skeletal structure
 * for all Operands. There are three types of operands:
 * <ol>
 * <li>Numeric literals - exact numerical representations.</li>
 * <li>Predefined constants</li>
 * <li>User definable, over-writable symbols (variables)</li>
 * </ol>
 *
 * @author Subhomoy Haldar
 * @version 1.0
 */
public interface Operand extends Token {
    /**
     * @return The value that this operand maps to.
     */
    double getValue();

    Operand add(Operand addend);

    Operand multiply(Operand multiplicand);

    Operand divide(Operand divisor);

    Operand pow(Operand exponent);

    Operand sine();

    Operand cosine();

    Operand tangent();

    Operand exp();

    Operand sqrt();
}
