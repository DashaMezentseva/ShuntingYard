package io.javaclasses.shuntingyard.token;

import io.javaclasses.shuntingyard.token.LeftParenthesesToken;
import io.javaclasses.shuntingyard.token.NumberToken;
import io.javaclasses.shuntingyard.token.OperatorToken;
import io.javaclasses.shuntingyard.token.RightParenthesesToken;

public interface Visitor {

        void visit(NumberToken token);

        void visit(OperatorToken token);

        void visit(LeftParenthesesToken token);

        void visit (RightParenthesesToken token);
}
