package com.github.subh0m0y.parser.token.operations;

import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.exceptions.EvaluationException;
import com.github.subh0m0y.parser.token.Operand;
import com.github.subh0m0y.parser.token.Operator;
import com.github.subh0m0y.parser.token.operands.Variable;
import com.github.subh0m0y.parser.token.VariableMap;

/**
 * @author Subhomoy Haldar
 * @version 2017.04.22
 */
public class Assignment extends Operator {
    public static final Assignment INSTANCE = new Assignment();

    private Assignment() {
        super("=", 2, ASSIGNMENT);
    }

    @Override
    public Operand evaluate(Operand... operands) throws
            ArityException,
            EvaluationException {
        check(operands.length);
        // Make sure that the operand being assigned to
        // is a variable.
        if (!(operands[0] instanceof Variable)) {
            throw new EvaluationException("Assignment only works for variables.");
        }
        VariableMap.INSTANCE.bind((Variable) operands[0], operands[1]);
        return operands[1];
    }
}
