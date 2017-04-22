package com.github.subh0m0y.parser.token;

import com.github.subh0m0y.parser.token.functions.Cosine;
import com.github.subh0m0y.parser.token.functions.Exponential;
import com.github.subh0m0y.parser.token.functions.Sine;
import com.github.subh0m0y.parser.token.functions.Tangent;
import com.github.subh0m0y.parser.token.operations.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class OperatorMap {
    public static OperatorMap INSTANCE = new OperatorMap();

    private final Map<String, Operator> operationMap;
    private final Set<String> symbolSet;

    private OperatorMap() {
        operationMap = Collections.unmodifiableMap(initializedMap());
        symbolSet = Collections.unmodifiableSet(operationMap.keySet());
    }

    private static Map<String, Operator> initializedMap() {
        Map<String, Operator> map = new HashMap<>();

        // The basic binary operators, used in infix form
        map.put(Assignment.INSTANCE.getSymbol(), Assignment.INSTANCE);
        map.put(Addition.INSTANCE.getSymbol(), Addition.INSTANCE);
        map.put(Multiplication.INSTANCE.getSymbol(), Multiplication.INSTANCE);
        map.put(Division.INSTANCE.getSymbol(), Division.INSTANCE);
        map.put(RaisingToPower.INSTANCE.getSymbol(), RaisingToPower.INSTANCE);

        // The functions, which are used in prefix form
        map.put(Sine.INSTANCE.getSymbol(), Sine.INSTANCE);
        map.put(Cosine.INSTANCE.getSymbol(), Cosine.INSTANCE);
        map.put(Tangent.INSTANCE.getSymbol(), Tangent.INSTANCE);
        map.put(Exponential.INSTANCE.getSymbol(), Exponential.INSTANCE);

        return map;
    }

    public Operator getFor(final char ch) {
        return getFor(String.valueOf(ch));
    }

    public Operator getFor(final String symbol) {
        return operationMap.get(symbol);
    }

}
