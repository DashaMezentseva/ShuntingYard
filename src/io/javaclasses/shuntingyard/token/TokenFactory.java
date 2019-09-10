package io.javaclasses.shuntingyard.token;

import io.javaclasses.shuntingyard.operator.*;

import java.util.HashMap;
import java.util.Map;

import static jdk.nashorn.internal.runtime.JSType.isNumber;

public class TokenFactory {

    private final Map<Character, Token> tokenMap = new HashMap<Character, Token>() {{

        put('(', new LeftParenthesesToken());
        put(')', new RightParenthesesToken());
        put('*', new OperatorToken(new MultiplyOperator(), MultiplyOperator.priority));
        put('/', new OperatorToken(new DivideOperator(), DivideOperator.priority));
        put('+', new OperatorToken(new SumOperator(), SumOperator.priority));
        put('-', new OperatorToken(new SubstractOperator(),SubstractOperator.priority));


    }};

    public Token createToken(String symbol) {

        if (symbol.matches("((-|\\\\+)?[0-9]+(\\\\.[0-9]+)?)+")){

            return new NumberToken(Double.parseDouble(symbol));
        }
        else {
            return tokenMap.get(symbol);
        }

    }


}
