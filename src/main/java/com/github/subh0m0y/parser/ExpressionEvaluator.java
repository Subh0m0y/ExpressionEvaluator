package com.github.subh0m0y.parser;

import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.exceptions.EvaluationException;
import com.github.subh0m0y.parser.exceptions.ImproperParenthesesException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;
import com.github.subh0m0y.parser.token.Token;

import java.util.List;
import java.util.Stack;

import static com.github.subh0m0y.parser.ExpressionTokenizer.*;

/**
 * This class takes an expression in the form of a String
 * and tries to evaluate it.
 *
 * @author Subhomoy Haldar
 * @version 1.0
 */
class ExpressionEvaluator {

    static Operand evaluate(final List<Token> tokens) throws
            ArityException,
            ImproperParenthesesException,
            UnrecognizedOperatorException,
            UnrecognizedCharacterException {
        Stack<Operand> stack = new Stack<>();

        // For empty token lists
        if (tokens.isEmpty()) {
            return null;
        }

        for (Token token : tokens) {
            if (token instanceof Operand) {
                stack.push((Operand) token);
            } else {
                // Token is an operator (which includes functions)
                Operator operator = (Operator) token;
                int arity = operator.getArity();
                if (stack.size() < arity) {
                    throw new ArityException(operator, arity, stack.size());
                }
                // The operands are popped in reverse order.
                // That is why the loop runs backwards.
                Operand[] operands = new Operand[arity];
                for (int i = arity - 1; i >= 0; i--) {
                    operands[i] = stack.pop();
                }
                Operand result = operator.evaluate(operands);
                stack.push(result);
            }
        }
        if (stack.size() > 1) {
            throw new EvaluationException("Too many operands. There might be an operator (or more) missing.");
        }
        if (stack.isEmpty()) {
            throw new EvaluationException("Internal error. No operands in stack.");
        }
        return stack.pop();
    }

}
