package io.javaclasses.shuntingyard.operator;

public abstract class Operator {

    int priority;
    boolean leftAssociative;

    public Operator(int priority) {
        this.priority = priority;
    }

    public abstract double solve(double numberFirst, double numberSecond);
}
