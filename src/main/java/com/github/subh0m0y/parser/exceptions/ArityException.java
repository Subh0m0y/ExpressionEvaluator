package com.github.subh0m0y.parser.exceptions;

import com.github.subh0m0y.parser.token.Operator;

/**
 * An ArityException is thrown when the number of operands required
 * by an Operator does not match the number of available operands.
 * <p>
 * Arity is defined as the number of arguments needed by a function.
 * It is used in a broader sense to cover the operators as well:
 * "binary", "unary", "ternary", etc.
 *
 * @author Subhomoy Haldar
 * @version 2017.04.20
 */
public class ArityException extends RuntimeException {
    /**
     * Creates a new {@link ArityException} with the information
     * necessary.
     *
     * @param operator      The operator concerned.
     * @param expectedArity The expected number of arguments.
     * @param actualArity   The actual number of arguments.
     */
    public ArityException(final Operator operator,
                          final int expectedArity,
                          final int actualArity) {
        super(String.format(
                "The number of operands expected for %s is %d. Found %d",
                operator.toString(), expectedArity, actualArity)
        );
    }
}
