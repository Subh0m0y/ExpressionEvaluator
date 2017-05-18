package com.github.subh0m0y.parser;

import com.github.subh0m0y.parser.exceptions.ImproperParenthesesException;
import com.github.subh0m0y.parser.token.*;
import com.github.subh0m0y.parser.token.operands.Real;
import com.github.subh0m0y.parser.token.operands.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.github.subh0m0y.parser.ExpressionTokenizer.*;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
class ExpressionConverter {

    static List<Token> convert(final List<Token> tokenList) throws
            ConversionException,
            ImproperParenthesesException,
            UnrecognizedOperatorException {

        List<Token> outputList = new ArrayList<>(tokenList.size());
        Stack<Token> stack = new Stack<>();

        for (Token token : tokenList) {
            if (token instanceof Real || token instanceof Variable) {
                // Token is a number, so push it straight onto the
                // output queue (list).
                outputList.add(token);
            } else if (token instanceof Operator) {
                // An operator refers to both binary operators
                // and functions, which appear in prefix notation
                final Operator operator = (Operator) token;
                if (operator.isFunction()) {
                    // No further processing required
                    stack.push(operator);
                } else {
                    // Operator precedence rules apply here
                    while (!stack.isEmpty()) {
                        // Keep popping operators with higher or equal
                        // precedence and pushing them to the output queue (list)
                        Token peekedToken = stack.peek();
                        if (peekedToken instanceof Operator &&
                                operator.getPriority()
                                        < ((Operator) peekedToken).getPriority()) {
                            outputList.add(stack.pop());
                        } else {
                            break;
                        }
                    }
                    stack.push(operator);
                }
            } else if (token instanceof ArgumentSeparator) {
                // The argument separator requires that all the things that
                // appeared after the left parenthesis and before it, must
                // evaluate to a single value.
                while (!(stack.isEmpty()
                        || stack.peek() instanceof LeftParenthesis)) {
                    outputList.add(stack.pop());
                }
                if (stack.isEmpty()) {
                    // No opening parenthesis was encountered
                    throw new ConversionException(
                            "Either argument separator (,) is misplaced, or " +
                                    "parentheses are unbalanced."
                    );
                }
            } else if (token instanceof LeftParenthesis) {
                stack.push(token);
            } else if (token instanceof RightParenthesis) {
                // Similar to the argument separator
                while (!(stack.peek() instanceof LeftParenthesis)) {
                    outputList.add(stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new ImproperParenthesesException(
                            "Mismatched parentheses; missing opening parenthesis."
                    );
                }
                // Remove left parenthesis
                stack.pop();
                // A pair of parentheses might succeed a function call
                if (!stack.isEmpty()) {
                    Token topToken = stack.peek();
                    if (topToken instanceof Operator && ((Operator) topToken).isFunction()) {
                        outputList.add(stack.pop());
                    }
                }
            }
        }
        // All the parentheses have been matched (hopefully, after two layers
        // of screening). Pop all the operators to the output list
        while (!stack.isEmpty()) {
            Token token = stack.pop();
            if (token instanceof LeftParenthesis || token instanceof RightParenthesis) {
                throw new ImproperParenthesesException("Mismatched parentheses.");
            }
            outputList.add(token);
        }
        return outputList;
    }

    /**
     * A custom exception class to encapsulate any errors that occur
     * during parsing.
     */
    static class ConversionException extends RuntimeException {
        ConversionException(String message) {
            super(message);
        }
    }
}
