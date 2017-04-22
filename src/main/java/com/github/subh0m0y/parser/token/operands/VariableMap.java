package com.github.subh0m0y.parser.token.operands;

import com.github.subh0m0y.parser.token.Operand;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Subhomoy Haldar
 * @version 2017.04.21
 */
public class VariableMap {
    public static final VariableMap INSTANCE = new VariableMap();

    private final Map<Variable, Operand> map;

    private VariableMap() {
        map = new HashMap<>();
        // Bind constants
        map.put(new Variable("pi"), new Real(Math.PI));
        map.put(new Variable("e"), new Real(Math.E));
    }

    public void bind(final Variable variable, final Operand operand) {
        map.put(variable, operand);
    }

    public Operand get(final Variable variable) {
        return map.get(variable);
    }
}
