package io.javaclasses.shuntingyard.token;

public interface Operand extends Token {

    double getValue();

    Operand add(Operand addend);

    Operand multiply(Operand multiplicand);

    Operand divide(Operand divisor);

}
