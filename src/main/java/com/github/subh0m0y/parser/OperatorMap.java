package com.github.subh0m0y.parser;

import com.github.subh0m0y.parser.token.Operator;
import com.github.subh0m0y.parser.token.functions.*;
import com.github.subh0m0y.parser.token.operations.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the class that binds the symbols of various operators
 * with their functionality. This is possible by using a standard
 * hierarchy for defining and gluing the functionality of the operands
 * with the various Operators.
 *
 * @author Subhomoy Haldar
 * @version 2017.05.18
 */
class OperatorMap {
    static final OperatorMap INSTANCE = new OperatorMap();

    private final Map<String, Operator> operationMap;

    private OperatorMap() {
        operationMap = Collections.unmodifiableMap(initializedMap());
    }

    /**
     * @return An initialized Map with all the functions bound to their symbols.
     */
    private static Map<String, Operator> initializedMap() {
        Map<String, Operator> map = new HashMap<>();

        // The basic binary operators, used in infix form
        map.put(Assignment.INSTANCE.getSymbol(), Assignment.INSTANCE);
        map.put(Addition.INSTANCE.getSymbol(), Addition.INSTANCE);
        map.put(Multiplication.INSTANCE.getSymbol(), Multiplication.INSTANCE);
        map.put(Division.INSTANCE.getSymbol(), Division.INSTANCE);
        map.put(RaisingToPower.INSTANCE.getSymbol(), RaisingToPower.INSTANCE);

        // The functions, which are used in prefix form
        map.put(Absolute.INSTANCE.getSymbol(), Absolute.INSTANCE);
        map.put(SquareRoot.INSTANCE.getSymbol(), SquareRoot.INSTANCE);
        map.put(Sine.INSTANCE.getSymbol(), Sine.INSTANCE);
        map.put(Cosine.INSTANCE.getSymbol(), Cosine.INSTANCE);
        map.put(Tangent.INSTANCE.getSymbol(), Tangent.INSTANCE);
        map.put(Exponential.INSTANCE.getSymbol(), Exponential.INSTANCE);
        map.put(Logarithm.INSTANCE.getSymbol(), Logarithm.INSTANCE);

        return map;
    }

    /**
     * @param character The character which uniquely defines an infix operator.
     * @return The uniquely identified binary operator.
     */
    Operator getFor(final char character) {
        return getFor(String.valueOf(character));
    }

    /**
     * @param symbol The function name which uniquely defines an operator.
     * @return The required operator (may be an infix operator or a function).
     */
    Operator getFor(final String symbol) {
        return operationMap.get(symbol);
    }

}
