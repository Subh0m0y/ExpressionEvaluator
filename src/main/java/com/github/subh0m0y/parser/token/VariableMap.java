package com.github.subh0m0y.parser.token;

import com.github.subh0m0y.parser.token.operands.Real;
import com.github.subh0m0y.parser.token.operands.Variable;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the class that provides the ability of assigning real values to
 * variables (with valid names) and accessing or reassigning the values
 * freely.
 *
 * @author Subhomoy Haldar
 * @version 2017.04.21
 */
public class VariableMap {
    /**
     * The variable map that is shared across all instances of the system.
     */
    public static final VariableMap INSTANCE = new VariableMap();

    private final Map<Variable, Operand> map;

    private VariableMap() {
        map = new HashMap<>();
        // Bind constants
        map.put(new Variable("pi"), new Real(Math.PI));
        map.put(new Variable("e"), new Real(Math.E));
        map.put(new Variable("phi"), new Real((1 + Math.sqrt(5)) / 2));
    }

    /**
     * Assigns a given operand (may be another variable) to a variable
     * with the desired name. If the variable hadn't been declared before,
     * it is initialized with the given value. Otherwise, it is updated.
     *
     * @param variable The variable to assign the given value to.
     * @param operand  The value to be assiged.
     */
    public void bind(final Variable variable, final Operand operand) {
        map.put(variable, operand);
    }

    /**
     * @param variable The variable whose value is sought.
     * @return The variable's value (if it is initialized) or null.
     */
    public Operand get(final Variable variable) {
        return map.get(variable);
    }
}
