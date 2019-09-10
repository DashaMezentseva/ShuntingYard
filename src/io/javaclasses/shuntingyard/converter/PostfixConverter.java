package io.javaclasses.shuntingyard.converter;

import io.javaclasses.shuntingyard.token.Token;
import io.javaclasses.shuntingyard.token.TokenFactory;
import io.javaclasses.shuntingyard.visitor.ShuntingYardVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PostfixConverter {

    private ShuntingYardVisitor visitor;

    public PostfixConverter(){
        this.visitor = new ShuntingYardVisitor();
    }

    public ArrayList<Token> convert(String expression) {

        TokenFactory factory = new TokenFactory();

        ShuntingYardVisitor visitor = new ShuntingYardVisitor();

        expression = expression.replace(" ", "").replace("(-", "(0-")
                .replace(",-", ",0-");
        if (expression.charAt(0) == '-') {
            expression = "0" + expression;
        }
        // splitting input string into tokens
        StringTokenizer stringTokenizer = new StringTokenizer(expression,
                "+-*/" + "()", true);


        while (stringTokenizer.hasMoreTokens()) {

            String lexeme = stringTokenizer.nextToken();
            Token token = factory.createToken(lexeme);
            token.acceptVisitor(visitor);
        }

        return visitor.getResult();
    }
}
