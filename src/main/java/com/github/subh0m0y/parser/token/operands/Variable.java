package com.github.subh0m0y.parser.token.operands;

import com.github.subh0m0y.parser.exceptions.EvaluationException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.VariableMap;

/**
 * This class is the functional, access point to the VariableMap
 * that provides the functionality to define and use variables.
 * <p>
 * During parsing, new Variable instances are always created.
 * However, when someone tries to use a variable that is
 * uninitialized,
 * <p>
 * A Variable is uniquely identified by its symbol. A symbol
 * is a String of characters which does not start with a digit
 * and is allowed to have letters, digits and underscores.
 *
 * @author Subhomoy Haldar
 * @version 2017.04.22
 */
public class Variable implements Operand {
    private final String symbol;

    /**
     * Creates a new instance of a Variable with the  given symbol.
     * The Variable may or may not be initialized.
     *
     * @param symbol The symbol to uniquely identify this variable.
     */
    public Variable(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return The value that has been assigned to this Variable.
     * @throws EvaluationException If this variable is uninitialized.
     */
    @Override
    public double getValue() throws EvaluationException {
        if (!isInitialized()) {
            throw new EvaluationException("Variable " + symbol + " is not initialized.");
        }
        return VariableMap.INSTANCE.get(this).getValue();
    }

    /**
     * If this variable is initialized, then it returns the
     * sum of its most recently assigned value and the given
     * Operand (which may be another Variable).
     *
     * @param addend The operand whose value to add to this.
     * @return The sum of its assigned value and the operand.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand add(Operand addend) throws EvaluationException {
        return new Real(getValue()).add(addend);
    }

    /**
     * If this variable is initialized, then it returns the
     * product of its most recently assigned value and the given
     * Operand (which may be another Variable).
     *
     * @param multiplicand The operand whose value to multiply with.
     * @return The product of its assigned value and the operand.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand multiply(Operand multiplicand) throws EvaluationException {
        return new Real(getValue()).multiply(multiplicand);
    }

    /**
     * If this variable is initialized, then it returns the
     * quotient of its most recently assigned value and the given
     * Operand (which may be another Variable).
     *
     * @param divisor The operand whose value to divide with.
     * @return The quotient of its assigned value and the operand.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand divide(Operand divisor) throws EvaluationException {
        return new Real(getValue()).divide(divisor);
    }

    /**
     * If this variable is initialized, then it returns
     * its most recently assigned value raised to the power of
     * the given Operand (which may be another Variable).
     *
     * @param exponent The operand whose value to raise this value to.
     * @return The result of raising its assigned value to the given value.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand pow(Operand exponent) throws EvaluationException {
        return new Real(getValue()).pow(exponent);
    }

    /**
     * If this variable is initialized, then calculate the sine
     * of its most recently assigned value.
     *
     * @return The sine of this variable's most recently assigned value.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand sine() throws EvaluationException {
        return new Real(getValue()).sine();
    }

    /**
     * If this variable is initialized, then calculate the cosine
     * of its most recently assigned value.
     *
     * @return The cosine of this variable's most recently assigned value.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand cosine() throws EvaluationException {
        return new Real(getValue()).cosine();
    }

    /**
     * If this variable is initialized, then calculate the tangent
     * of its most recently assigned value.
     *
     * @return The tangent of this variable's most recently assigned value.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand tangent() throws EvaluationException {
        return new Real(getValue()).tangent();
    }

    /**
     * If this variable is initialized, then calculate the value of
     * raising e to its most recently assigned value (exp(x)).
     *
     * @return e<sup>this variable's most recently assigned value</sup>.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand exp() throws EvaluationException {
        return new Real(getValue()).exp();
    }

    /**
     * If this variable is initialized, then calculate the square root
     * of its most recently assigned value.
     *
     * @return The square root of this variable's most recently assigned value.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand sqrt() throws EvaluationException {
        return new Real(getValue()).sqrt();
    }

    /**
     * If this variable is initialized, then calculates the absolute value
     * of its most recently assigned value.
     *
     * @return The absolute value of this variable's most recently assigned value.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand abs() throws EvaluationException {
        return new Real(getValue()).abs();
    }

    /**
     * If this variable is initialized, then calculates the natural logarithm
     * of its most recently assigned value.
     *
     * @return The natural logarithm of this variable's most recently assigned value.
     * @throws EvaluationException If the variable is uninitialized or its value
     *                             is negative.
     */
    @Override
    public Operand log() throws EvaluationException {
        return new Real(getValue()).log();
    }

    /**
     * An internal function that checks whether this Variable has been
     * assigned a value before. If it has, the VariableMap will have
     * an instance of it.
     *
     * @return {@code true} if the VariableMap has an instance of this
     * Variable.
     */
    private boolean isInitialized() {
        return VariableMap.INSTANCE.get(this) != null;
    }

    /**
     * Returns a String representation of this Variable. It always
     * returns the symbol. If the variable is initialized, then its
     * rounded off value is concatenated in the form
     * <pre>
     *     {@code symbol + " = " + value (rounded)}
     * </pre>
     *
     * @return A String representation of this Variable.
     */
    @Override
    public String toString() {
        return symbol + (isInitialized()
                ? " = " + String.format("%.2f", getValue())
                : "");
    }

    /**
     * Compares the given object and (if it is a Variable and) it has
     * has the same symbol.
     *
     * @param obj The object to compare against.
     * @return {@code true} if the two objects represent the same variable.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && symbol.equals(((Variable) obj).symbol);
    }

    /**
     * Calculates the hashCode of the Variable based on the symbol.
     * This is important for proper functioning with the VariableMap.
     *
     * @return The hashCode of this Variable based off its symbol.
     */
    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
