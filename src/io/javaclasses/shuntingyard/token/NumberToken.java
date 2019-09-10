package io.javaclasses.shuntingyard.token;

public class NumberToken implements Token {

    public final Double numValue;

    public NumberToken(Double numValue) {
        this.numValue = numValue;
    }

    @Override
    public String toString() {
        return super.toString() + " Value: " + numValue;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {

        visitor.visit(this);
    }
}
