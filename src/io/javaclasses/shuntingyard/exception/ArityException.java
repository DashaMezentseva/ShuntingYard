package io.javaclasses.shuntingyard.exception;

import io.javaclasses.shuntingyard.token.Operator;

public class ArityException extends RuntimeException {

    public ArityException(final Operator operator,
                          final int expectedArity,
                          final int actualArity) {
        super(String.format(
                operator.toString(), expectedArity, actualArity)
        );
    }
}
