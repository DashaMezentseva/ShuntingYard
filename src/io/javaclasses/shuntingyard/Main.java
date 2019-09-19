package io.javaclasses.shuntingyard;

import io.javaclasses.shuntingyard.exception.ArityException;
import io.javaclasses.shuntingyard.exception.EvaluationException;
import io.javaclasses.shuntingyard.exception.ImproperParenthesesException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        runLoop(System.in, System.out);
    }


    public static void runLoop(final InputStream inputStream,
                               final PrintStream outputStream) {
        Scanner scanner = new Scanner(inputStream);

        boolean exit = false;
        do {

            String input = scanner.nextLine().trim();
            if (input.isEmpty()
                    || input.equalsIgnoreCase("exit")
                    || input.equalsIgnoreCase("quit")) {
                exit = true;
                continue;
            }

            try {
                final Expression expression = new Expression(input);
                outputStream.println(expression.evaluate().toString());
            } catch (ArityException
                    | ExpressionConverter.ConversionException
                    | EvaluationException
                    | ImproperParenthesesException
                    | ExpressionTokenizer.UnrecognizedOperatorException
                    | ExpressionTokenizer.UnrecognizedCharacterException e) {
                outputStream.println("ERROR: " + e.getMessage());
            }
        } while (!exit);
    }

}
