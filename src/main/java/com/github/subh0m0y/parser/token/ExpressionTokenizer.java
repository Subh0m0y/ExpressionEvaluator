package com.github.subh0m0y.parser.token;

import com.github.subh0m0y.parser.exceptions.ImproperParenthesesException;
import com.github.subh0m0y.parser.token.operands.Real;
import com.github.subh0m0y.parser.token.operands.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class processes an input String and returns a List
 * of tokens. A token is either an Operand or an Operator.
 *
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class ExpressionTokenizer {


    public static void main(String[] args) throws UnrecognizedOperatorException, ImproperParenthesesException, UnrecognizedCharacterException {
        System.out.println(tokenize("1+-2"));
        System.out.println(tokenize("x+y"));
        System.out.println(tokenize("2sin(a)cos(b)"));
        System.out.println(tokenize("1+(2^(4-3))sin(4pi/2)"));
        System.out.println(tokenize("1x+2/3(yes*sin(3pi/2))"));
    }

    private static final OperatorMap MAP = OperatorMap.INSTANCE;
    private static final String OP_CHARS = "+*/^=";
    private static final String NUM_CHARS = "-.";
    private static final String VAR_CHARS = "_";

    public static List<Token> tokenize(final String input) throws
            UnrecognizedOperatorException,
            ImproperParenthesesException,
            UnrecognizedCharacterException {
        // Remove all whitespace
        String modifiedInput = input.replaceAll("\\s+", "");
        // Replace -variable or -literal with -1*(...)
        modifiedInput = modifiedInput.replaceAll("-", "+-1*");
        // If it was "(-x", it changed to "(+-1*x"
        // Remove redundant "+" following "("
        modifiedInput = modifiedInput.replaceAll("\\(\\+", "(");
        // Same for leading plus signs
        if (modifiedInput.startsWith("+")) {
            modifiedInput = modifiedInput.substring(1);
        }
        return tokenize(modifiedInput.toCharArray());
    }

    private static List<Token> tokenize(final char[] chars) throws
            UnrecognizedOperatorException,
            UnrecognizedCharacterException,
            ImproperParenthesesException {
        // The final tokens are appended to this list
        List<Token> tokenList = new ArrayList<>(chars.length);
        // Numeric literals are generated character by character
        StringBuilder numericBuffer = new StringBuilder(chars.length);
        // So are variables and functions (basically prefix operators)
        StringBuilder lettersBuffer = new StringBuilder(chars.length);

        // Also keep track of parentheses. Illegal states occur when
        // the count != 0 at the end of iteration, implying unbalanced
        // parentheses, or count drops below zero during iteration,
        // implying closing parenthesis occurs before opening parenthesis
        int parenthesesTally = 0;

        for (char ch : chars) {
            if (lettersBuffer.length() == 0
                    && Character.isDigit(ch)
                    || NUM_CHARS.indexOf(ch) > -1) {
                // The definition of a numeric literal is
                // that it can have only have digits and - and .
                numericBuffer.append(ch);
            } else if (Character.isLetterOrDigit(ch) || VAR_CHARS.indexOf(ch) > -1) {
                // Encountering a letter might be because of a variable
                // or a function.
                if (numericBuffer.length() > 0) {
                    // A numeric literal precedes this. Process it.
                    emptyNumericBufferAsLiteral(numericBuffer, tokenList);
                    // 2x => 2 * x
                    tokenList.add(MAP.getFor("*"));
                }
                lettersBuffer.append(ch);
            } else if (OP_CHARS.indexOf(ch) > -1) {
                // We have found a one-letter, binary operand
                if (numericBuffer.length() > 0) {
                    emptyNumericBufferAsLiteral(numericBuffer, tokenList);
                } else if (lettersBuffer.length() > 0) {
                    emptyLettersBufferAsVariable(lettersBuffer, tokenList);
                }
                tokenList.add(MAP.getFor(ch));
            } else if (ch == '(') {
                parenthesesTally++;
                // If there were letters preceding it, then the following
                // must be the list of arguments for that function.
                // eg. sin(, cos(, tan(, exp(, ...
                if (lettersBuffer.length() > 0) {
                    emptyLettersBufferAsFunction(lettersBuffer, tokenList);
                } else if (numericBuffer.length() > 0) {
                    // There was a literal before it. It must be
                    // multiplied to the part in parentheses
                    // ... 3(... => ... 3 * (...
                    emptyNumericBufferAsLiteral(numericBuffer, tokenList);
                    tokenList.add(MAP.getFor("*"));
                } else if (tokenList.size() > 0 &&
                        tokenList.get(tokenList.size() - 1) // last element added
                                instanceof RightParenthesis) {
                    // "..)(.." => "...)*(..."
                    tokenList.add(MAP.getFor("*"));
                }
                tokenList.add(new LeftParenthesis());
            } else if (ch == ')') {
                // Validate first
                parenthesesTally--;
                if (parenthesesTally < 0) {
                    throw new ImproperParenthesesException(
                            "Closing parenthesis occurs before opening parenthesis."
                    );
                }
                // check if it closed abruptly, i.e. "()" appears
                if (tokenList.size() > 0
                        && tokenList.get(tokenList.size() - 1) // last element added
                        instanceof LeftParenthesis
                        && lettersBuffer.length() == 0
                        && numericBuffer.length() == 0) {
                    // i.e. "...()..." is NOT allowed
                    throw new ImproperParenthesesException("Empty parentheses pair.");
                }
                if (lettersBuffer.length() > 0) {
                    emptyLettersBufferAsVariable(lettersBuffer, tokenList);
                } else if (numericBuffer.length() > 0) {
                    emptyNumericBufferAsLiteral(numericBuffer, tokenList);
                }
                tokenList.add(new RightParenthesis());
            } else if (ch == ',') {
                // This is a separator for the arguments of a function
                if (lettersBuffer.length() > 0) {
                    emptyLettersBufferAsVariable(lettersBuffer, tokenList);
                } else if (numericBuffer.length() > 0) {
                    emptyNumericBufferAsLiteral(numericBuffer, tokenList);
                }
                tokenList.add(new ArgumentSeparator());
            } else {
                // Unrecognised character
                throw new UnrecognizedCharacterException(ch);
            }
        }
        // The final check to see if the parentheses were balanced.
        if (parenthesesTally != 0) {
            throw new ImproperParenthesesException(
                    "Unbalanced parentheses. Closing parenthesis missing."
            );
        }
        // Empty the buffers
        if (numericBuffer.length() > 0) {
            emptyNumericBufferAsLiteral(numericBuffer, tokenList);
        }
        if (lettersBuffer.length() > 0) {
            emptyLettersBufferAsVariable(lettersBuffer, tokenList);
        }
        return tokenList;
    }

    private static void emptyLettersBufferAsFunction(StringBuilder lettersBuffer,
                                                     List<Token> tokenList)
            throws UnrecognizedOperatorException {
        final String bufferContents = lettersBuffer.toString();
        final Operator operator = MAP.getFor(bufferContents);
        if (operator == null) {
            throw new UnrecognizedOperatorException(bufferContents);
        }
        tokenList.add(operator);
        lettersBuffer.delete(0, lettersBuffer.length());
    }

    private static void emptyLettersBufferAsVariable(StringBuilder lettersBuffer,
                                                     List<Token> tokenList) {
        tokenList.add(new Variable(lettersBuffer.toString()));
        lettersBuffer.delete(0, lettersBuffer.length());
    }

    private static void emptyNumericBufferAsLiteral(StringBuilder numericBuffer,
                                                    List<Token> tokenList) {
        tokenList.add(new Real(numericBuffer.toString()));
        numericBuffer.delete(0, numericBuffer.length());
    }

    public static class UnrecognizedCharacterException extends RuntimeException {
        UnrecognizedCharacterException(char ch) {
            super("Unrecognised character encountered while parsing: " + ch);
        }
    }

    /**
     * This exception occurs if a function required in an expression
     * is not defined by the system. This might be due to a typographic
     * error on the part of the user or lack of support of that function
     * in this library.
     *
     * @author Subhomoy Haldar
     * @version 2017.04.20
     */
    public static class UnrecognizedOperatorException extends RuntimeException {
        /**
         * Create a new {@link UnrecognizedOperatorException} with
         * the unrecognised function name.
         *
         * @param tokenString The name of the unknown function.
         */
        UnrecognizedOperatorException(final String tokenString) {
            super("Unrecognised operation : " + tokenString);
        }
    }
}
