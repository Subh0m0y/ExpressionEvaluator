package com.github.subh0m0y.parser.token.operands;

import com.github.subh0m0y.parser.exceptions.EvaluationException;
import com.github.subh0m0y.parser.token.Operand;

/**
 * Represents a simple immutable real valued numerical
 * operand.
 *
 * @author Subhomoy Haldar
 * @version 2017.04.20
 */
public class Real implements Operand {
    private final double value;

    /**
     * Creates an immutable numerical literal from its
     * String representation.
     *
     * @param value The String representation of this literal.
     * @throws NumberFormatException If the String representation provided
     *                               is malformed.
     */
    public Real(final String value) throws NumberFormatException {
        this.value = Double.parseDouble(value);
    }

    /**
     * The constructor for an immutable numerical literal
     * operand.
     *
     * @param value The real value of this operand.
     */
    public Real(final double value) {
        this.value = value;
    }

    /**
     * @return The real value encapsulated by this operand.
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * Adds the Operand provided and returns the result of addition
     * of the real values (approximations) of the two.
     *
     * @param addend The operand to add, the addend.
     * @return The sum of this operand and the addend.
     */
    @Override
    public Operand add(Operand addend) {
        return new Real(value + addend.getValue());
    }

    /**
     * Multiplies the Operand provided and returns the result of
     * multiplication of the real values (approximations) of the two.
     *
     * @param multiplicand The operand to multiply, the multiplicand.
     * @return The product of this operand and the multiplicand.
     */
    @Override
    public Operand multiply(Operand multiplicand) {
        return new Real(value * multiplicand.getValue());
    }

    /**
     * Divided this Real by the Operand provided and returns the
     * result of quotient of the real values (approximations) of the two.
     *
     * @param divisor The operand to divide this with, the divisor.
     * @return The quotient of this Real divided by the divisor.
     */
    @Override
    public Operand divide(Operand divisor) {
        return new Real(value / divisor.getValue());
    }

    /**
     * Returns the result of raising this Real by the given
     * operand.
     *
     * @param exponent The power to raise this Real to.
     * @return This real operand raised to the given value.
     */
    @Override
    public Operand pow(Operand exponent) {
        return new Real(Math.pow(value, exponent.getValue()));
    }

    /**
     * Returns the sine of this real number.
     *
     * @return The sine of this real number.
     * @see Math#sin(double)
     */
    @Override
    public Operand sine() {
        return new Real(Math.sin(value));
    }

    /**
     * Returns the cosine of this real number.
     *
     * @return The cosine of this real number.
     * @see Math#cos(double)
     */
    @Override
    public Operand cosine() {
        return new Real(Math.cos(value));
    }

    /**
     * Returns the tangent of this real number.
     *
     * @return The tangent of this real number.
     * @see Math#tan(double)
     */
    @Override
    public Operand tangent() {
        return new Real(Math.tan(value));
    }

    /**
     * Returns the result of raising Euler's number (e) to
     * this value.
     *
     * @return e<sup>this</sup>.
     * @see Math#exp(double)
     */
    @Override
    public Operand exp() {
        return new Real(Math.exp(value));
    }

    /**
     * Returns the principal square-root of this Real value. If
     * it is negative, then an {@link EvaluationException} is
     * thrown.
     *
     * @return The principal square-root of this Real value.
     * @throws EvaluationException If its value is negative.
     */
    @Override
    public Operand sqrt() throws EvaluationException {
        if (value < 0) {
            throw new EvaluationException(
                    "Cannot calculate square root of a negative real number."
            );
        }
        return new Real(Math.sqrt(value));
    }

    /**
     * Returns the absolute, non-negative magnitude of this Real
     * value. That is returns  itself if the value is positive, or
     * the additive inverse of itself (-this) if it is negative.
     *
     * @return The absolute, non-negative magnitude of this Real
     * value.
     */
    @Override
    public Operand abs() {
        return new Real(Math.abs(value));
    }

    /**
     * Returns the natural logarithm of this Real value. If
     * it is negative, then an {@link EvaluationException} is
     * thrown.
     *
     * @return The natural logarithm of this Real value.
     * @throws EvaluationException If its value is negative.
     */
    public Operand log() throws EvaluationException {
        if (value < 0) {
            throw new EvaluationException(
                    "Cannot calculate logarithm of a negative real number."
            );
        }
        return new Real(Math.log(value));
    }

    /**
     * @return The desired String representation of this operand.
     */
    @Override
    public String toString() {
        return String.format("%.2f", value);
    }
}
