package com.github.subh0m0y.parser;

import com.github.subh0m0y.parser.exceptions.EvaluationException;
import com.github.subh0m0y.parser.exceptions.ArityException;
import com.github.subh0m0y.parser.exceptions.ImproperParenthesesException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static com.github.subh0m0y.parser.ExpressionConverter.*;
import static com.github.subh0m0y.parser.ExpressionTokenizer.*;

/**
 * @author Subhomoy Haldar
 * @version 2017.04.21
 */
public class REPL {
    public static void main(String[] args) {
        runLoop(System.in, System.out);
    }

    private static final String PROMPT = ">> ";
    private static final String HELP_MESSAGE = "\n========== Help Message ==========" +
            "\n Enter complete expressions on each line." +
            "\n Press Return to evaluate." +
            "\n" +
            "\n Supported operations are +, -, *, /, ^ (exponentiation)." +
            "\n Some more functions supported are:" +
            "\n 1. Sine - sin(x)" +
            "\n 2. Cosine - cos(x)" +
            "\n 3. Tangent - tan(x)" +
            "\n 4. Exponential - exp(x)" +
            "\n 5. Square root - sqrt(x)" +
            "\n\n To display this message again, type 'help'" +
            "\n in a new line. To exit, type 'exit' or 'quit'.";

    public static void runLoop(final InputStream inputStream,
                               final PrintStream outputStream) {
        Scanner scanner = new Scanner(inputStream);
        outputStream.println(HELP_MESSAGE);
        boolean exit = false;
        do {
            outputStream.print(PROMPT);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()
                    || input.equalsIgnoreCase("exit")
                    || input.equalsIgnoreCase("quit")) {
                exit = true;
                continue;
            }
            if (input.equalsIgnoreCase("help")) {
                outputStream.println(HELP_MESSAGE);
                continue;
            }
            try {
                final Expression expression = new Expression(input);
                outputStream.println(expression.evaluate().toString());
            } catch (ArityException
                    | ConversionException
                    | EvaluationException
                    | ImproperParenthesesException
                    | UnrecognizedOperatorException
                    | UnrecognizedCharacterException e) {
                outputStream.println("ERROR: " + e.getMessage());
            }
        } while (!exit);
    }

}
