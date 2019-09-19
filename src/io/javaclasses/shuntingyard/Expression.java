package io.javaclasses.shuntingyard;

import io.javaclasses.shuntingyard.exception.ArityException;
import io.javaclasses.shuntingyard.exception.ImproperParenthesesException;
import io.javaclasses.shuntingyard.token.Operand;
import io.javaclasses.shuntingyard.token.Token;

import java.util.Collections;
import java.util.List;

public class Expression {
    private final List<Token> tokens;

    public Expression(final String expressionString) throws
            ExpressionConverter.ConversionException,
            ImproperParenthesesException,
            ExpressionTokenizer.UnrecognizedCharacterException,
            ExpressionTokenizer.UnrecognizedOperatorException {
        tokens = Collections.unmodifiableList(
                ExpressionConverter.convert(
                        ExpressionTokenizer.tokenize(expressionString)
                )
        );
    }

    public Operand evaluate() throws
            ArityException,
            ExpressionConverter.ConversionException {
        return ExpressionEvaluator.evaluate(tokens);
    }

}
