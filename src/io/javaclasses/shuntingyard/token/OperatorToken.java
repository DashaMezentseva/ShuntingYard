package io.javaclasses.shuntingyard.token;

import io.javaclasses.shuntingyard.operator.Operator;

public class OperatorToken implements Token{

    public Operator operator;
    public int priority;

    public OperatorToken(Operator operator, int priority) {
        this.operator = operator;
        this.priority = priority;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }
}

