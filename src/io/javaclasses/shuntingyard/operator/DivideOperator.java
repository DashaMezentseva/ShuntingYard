package io.javaclasses.shuntingyard.operator;

public class DivideOperator extends Operator {

    public static int priority = 0;

    public DivideOperator() {
        super(0);
    }

    @Override
    public double solve(double numberFirst, double numberSecond) {
        return numberFirst / numberSecond;
    }
}
