package com.github.subh0m0y.parser.token;

/**
 * A token is the atomic component of an Expression. It
 * can either be an Operand - a numerical value or pre-defined
 * variable or symbol, or an Operator - which acts on one or
 * more operands.
 * <p>
 * This interface basically serves as the head of the hierarchy
 * and provides methods that must be implemented by both subclasses.
 *
 * @author Subhomoy Haldar
 * @version 1.0
 */
public interface Token {
    /**
     * @return The simplest possible String representation. This
     * can be used to convert the internal ExpressionTree back to
     * a String format.
     */
    @Override
    String toString();
}
