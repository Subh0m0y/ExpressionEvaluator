package com.github.subh0m0y.parser.token;

/**
 * Represents a token whose purpose is to delimit the Argument
 * tokens in an Expression.
 *
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class ArgumentSeparator implements Token {
    @Override
    public String toString() {
        return ",";
    }
}
