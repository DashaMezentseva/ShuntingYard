package io.javaclasses.shuntingyard.visitor;

import io.javaclasses.shuntingyard.exception.EvaluationException;
import io.javaclasses.shuntingyard.token.*;

import java.util.ArrayList;
import java.util.Stack;

public class ShuntingYardVisitor implements Visitor {

    private ArrayList<Token> outputQueue;

    private Stack<Token> operatorStack;

    @Override
    public void visit(NumberToken token) {
        outputQueue.add(token);
    }

    @Override
    public void visit(OperatorToken token) {
        Token top = operatorStack.peek();
        if (operatorStack.isEmpty() || leftParenthesisOnTop() || lowerPriorityFunctionOnTop(token)) {
            operatorStack.push(token);
        } else {
            while (!operatorStack.isEmpty() && top.getClass() == OperatorToken.class && GreaterOrEqualPriorityFunctionOnTop(token)) {
                outputQueue.add(operatorStack.pop());
                top = operatorStack.peek();
            }
            operatorStack.push(token);
        }
    }

    private boolean lowerPriorityFunctionOnTop(OperatorToken token) {
        Token top = operatorStack.peek();
        return top.getClass() == OperatorToken.class && ((OperatorToken) top).priority < token.priority;
    }

    private boolean GreaterOrEqualPriorityFunctionOnTop(OperatorToken token) {
        Token top = operatorStack.peek();
        return ((OperatorToken) top).priority >= token.priority;
    }

    @Override
    public void visit(RightParenthesesToken token) {
        while (!operatorStack.empty() && !leftParenthesisOnTop()) {
            outputQueue.add(operatorStack.pop());
        }
        if (!operatorStack.empty()) {
            operatorStack.pop();
        } else {
            throw new EvaluationException("Left parenthesis missing!");
        }
    }


    private boolean leftParenthesisOnTop() {
        return operatorStack.peek().getClass() == LeftParenthesesToken.class;
    }


    @Override
    public void visit(LeftParenthesesToken token) {
        operatorStack.push(token);
    }

    public ArrayList<Token> getResult() {
        while (!operatorStack.empty()) {
            outputQueue.add(operatorStack.pop());
        }
        return outputQueue;
    }
}
