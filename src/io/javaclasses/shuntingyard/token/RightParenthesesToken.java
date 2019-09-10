package io.javaclasses.shuntingyard.token;

public class RightParenthesesToken implements Token {

    @Override
    public void acceptVisitor(Visitor visitor) {

        visitor.visit(this);
    }
}
