package com.github.subh0m0y.parser;

import com.github.subh0m0y.parser.ShuntingYardExpressionConverter.ConversionException;
import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.exceptions.ImproperParenthesesException;
import com.github.subh0m0y.parser.token.ExpressionTokenizer.UnrecognizedCharacterException;
import com.github.subh0m0y.parser.token.ExpressionTokenizer.UnrecognizedOperatorException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Token;

import java.util.Collections;
import java.util.List;

/**
 * @author Subhomoy Haldar
 * @version 2017.04.21
 */
public class Expression {
    private final List<Token> tokens;

    public Expression(final String expressionString) throws
            ConversionException,
            ImproperParenthesesException,
            UnrecognizedCharacterException,
            UnrecognizedOperatorException {
        tokens = Collections.unmodifiableList(
                ShuntingYardExpressionConverter.convert(expressionString)
        );
    }

    public Operand evaluate() throws
            ArityException,
            ConversionException {
        return ExpressionEvaluator.evaluate(tokens);
    }

}
