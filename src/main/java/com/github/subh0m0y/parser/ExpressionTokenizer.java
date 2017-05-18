package com.github.subh0m0y.parser;

import com.github.subh0m0y.parser.exceptions.ImproperParenthesesException;
import com.github.subh0m0y.parser.token.*;
import com.github.subh0m0y.parser.token.operands.Real;
import com.github.subh0m0y.parser.token.operands.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class processes an input String and returns a List
 * of tokens. A token is either an Operand or an Operator.
 * It must be noted that the List of Tokens cannot be evaluated
 * directly. Use an ExpressionConverter (like the {@link com.github.subh0m0y.parser.ExpressionConverter})
 * to obtain a list which can be evaluated efficiently and correctly.
 * correctly.
 *
 * @author Subhomoy Haldar
 * @version 2017.05.18
 */
class ExpressionTokenizer {
    // A short alias for the OperationMap's instance.
    private static final OperatorMap MAP = OperatorMap.INSTANCE;
    // Characters that are valid for operators
    private static final String OP_CHARS = "+*/^=";
    // Non-numeric characters that are needed to represent numbers
    private static final String NUM_CHARS = "-.";
    // Non-alphabetic characters that can be a part of variable names
    private static final String VAR_CHARS = "_";

    /**
     * This method is the the first step carried out in the expression
     * evaluation process. Here, the raw input String is parsed and
     * broken down into various types of Tokens, which include Operators
     * and Operands like Numeric literals (Reals) and Variables.
     * <p>
     * The following things are to be considered before using this
     * method:
     * <ol>
     * <li>All whitespace is ignored in the input String.</li>
     * <li>Variables with names longer than one character are
     * supported. The valid characters for a variable include
     * uppercase and lowercase letters, letters and underscore.
     * The variable must not start with a digit.</li>
     * <li>Functions are parsed in the same way and the way
     * to distinguish the two is that functions are followed by
     * a comma-separated list of arguments in parentheses.</li>
     * <li>If numeric literals appear before a variable or a
     * function, it will be multiplied. For example, "2x" will
     * be equivalent to "2*x".</li>
     * <li>If parentheses are unbalanced, or are empty or the
     * arguments are not properly provided, then exceptions will
     * be thrown.</li>
     * <li>Wherever applicable, the presence of opening parentheses or
     * closing parentheses near literals and variables will be
     * regarded as implicit multiplication.</li>
     * </ol>
     *
     * @param input The raw input String to parse and tokenize.
     * @return A list of Tokens after the input String has been partially
     * validated and fully parsed and tokenized.
     * @throws UnrecognizedOperatorException  If the tokenizer encounters an
     *                                        unrecognized character (as binary
     *                                        operator) or unknown function.
     * @throws ImproperParenthesesException   If parentheses are improperly ordered,
     *                                        missing, unbalanced, etc.
     * @throws UnrecognizedCharacterException If an unrecognized character was
     *                                        encountered while parsing.
     */
    static List<Token> tokenize(final String input) throws
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

    /**
     * After the input String has been modified to make parsing
     * easier, the real action begins here.
     */
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

    /**
     * This exception is thrown when an unrecognized, unknown character is
     * encountered while parsing the input String.
     */
    static class UnrecognizedCharacterException extends RuntimeException {
        /**
         * Create a new {@link UnrecognizedCharacterException} with
         * the unrecognized character.
         *
         * @param unknown The unrecognized character.
         */
        UnrecognizedCharacterException(final char unknown) {
            super("Unrecognised character encountered while parsing: " + unknown);
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
    static class UnrecognizedOperatorException extends RuntimeException {
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
