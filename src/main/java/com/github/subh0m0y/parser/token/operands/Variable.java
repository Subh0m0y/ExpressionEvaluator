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
     * @param symbol
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
     * of it's most recently assigned value.
     *
     * @return The sine of this variables most recently assigned value.
     * @throws EvaluationException If the variable is uninitialized.
     */
    @Override
    public Operand sine() throws EvaluationException {
        return new Real(getValue()).sine();
    }

    /**
     *
     * @return
     */
    @Override
    public Operand cosine() {
        return new Real(getValue()).cosine();
    }

    @Override
    public Operand tangent() {
        return new Real(getValue()).tangent();
    }

    @Override
    public Operand exp() {
        return new Real(getValue()).exp();
    }

    private boolean isInitialized() {
        return VariableMap.INSTANCE.get(this) != null;
    }

    @Override
    public String toString() {
        return symbol + (isInitialized()
                ? " = " + String.format("%.2f", getValue())
                : "");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && symbol.equals(((Variable) obj).symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
