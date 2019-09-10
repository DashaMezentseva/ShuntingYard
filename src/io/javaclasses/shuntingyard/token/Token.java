package io.javaclasses.shuntingyard.token;

public interface Token {

    void acceptVisitor(Visitor visitor);

}
