package com.github.subh0m0y.parser.exceptions;

/**
 * This Exception serves as the generic wrapper for indicating any
 * anomalies that occur in Expressions relating to improper parentheses.
 * <p>
 * The anomalies may include missing or unpaired parenthesis, wrong
 * ordering of parentheses and so on.
 *
 * @author Subhomoy Haldar
 * @version 2017.04.20
 */
public class ImproperParenthesesException extends RuntimeException {
    /**
     * Create a new {@link ImproperParenthesesException} with
     * custom error message to clarify the error.
     *
     * @param message The message to be displayed.
     */
    public ImproperParenthesesException(final String message) {
        super(message);
    }
}
