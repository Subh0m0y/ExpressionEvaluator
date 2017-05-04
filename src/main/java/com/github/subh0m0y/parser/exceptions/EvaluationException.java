package com.github.subh0m0y.parser.exceptions;

/**
 * A class to encapsulate any Exceptions that might occur
 * during execution of the evaluation algorithm.
 *
 * @author Subhomoy Haldar
 * @version 2017.04.22
 */
public class EvaluationException extends RuntimeException {
    /**
     * @param message The message in detail.
     */
    public EvaluationException(String message) {
        super(message);
    }
}
