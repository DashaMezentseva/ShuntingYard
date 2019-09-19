package io.javaclasses.shuntingyard;

import io.javaclasses.shuntingyard.exception.ImproperParenthesesException;
import io.javaclasses.shuntingyard.token.LeftParenthesesToken;
import io.javaclasses.shuntingyard.token.Operator;
import io.javaclasses.shuntingyard.token.RightParenthesesToken;
import io.javaclasses.shuntingyard.token.Token;


import io.javaclasses.shuntingyard.token.operands.Real;
import io.javaclasses.shuntingyard.token.operands.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class ExpressionConverter {

    static List<Token> convert(final List<Token> tokenList) throws
            ConversionException,
            ImproperParenthesesException,
            ExpressionTokenizer.UnrecognizedOperatorException {

        List<Token> outputList = new ArrayList<>(tokenList.size());
        Stack<Token> stack = new Stack<>();

        for (Token token : tokenList) {
            if (token instanceof Real || token instanceof Variable) {
                // Token is a number, so push it straight onto the
                // output queue (list).
                outputList.add(token);
            } else if (token instanceof Operator) {
                final Operator operator = (Operator) token;
                if (operator.isFunction()) {
                    // No further processing required
                    stack.push(operator);
                } else {
                    while (!stack.isEmpty()) {
                        // Keep popping operators with higher or equal
                        // precedence and pushing them to the output queue (list)
                        Token peekedToken = stack.peek();
                        if (peekedToken instanceof Operator &&
                                operator.getPriority()
                                        < ((Operator) peekedToken).getPriority()) {
                            outputList.add(stack.pop());
                        } else {
                            break;
                        }
                    }
                    stack.push(operator);
                }
            } else if (token instanceof LeftParenthesesToken) {
                stack.push(token);
            } else if (token instanceof RightParenthesesToken) {
                while (!(stack.peek() instanceof LeftParenthesesToken)) {
                    outputList.add(stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new ImproperParenthesesException(
                            "Mismatched parentheses; missing opening parenthesis."
                    );
                }
                // Remove left parenthesis
                stack.pop();
                // A pair of parentheses might succeed a function call
                if (!stack.isEmpty()) {
                    Token topToken = stack.peek();
                    if (topToken instanceof Operator && ((Operator) topToken).isFunction()) {
                        outputList.add(stack.pop());
                    }
                }
            }
        }

        while (!stack.isEmpty()) {
            Token token = stack.pop();
            if (token instanceof LeftParenthesesToken || token instanceof RightParenthesesToken) {
                throw new ImproperParenthesesException("Mismatched parentheses.");
            }
            outputList.add(token);
        }
        return outputList;
    }

    static class ConversionException extends RuntimeException {
        ConversionException(String message) {
            super(message);
        }
    }
}
