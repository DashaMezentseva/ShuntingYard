package io.javaclasses.shuntingyard.token;


public class LeftParenthesesToken implements Token {

    private void setTypesByLiteral(String literal){

    }

    @Override
    public void acceptVisitor(Visitor visitor) {

        visitor.visit(this);
    }
}
