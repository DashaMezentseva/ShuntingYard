package io.javaclasses.shuntingyard.operator;

public class SumOperator extends Operator {

    public static int priority = 1;

    public SumOperator() {
        super(1);
    }

    @Override
    public double solve(double numberFirst, double numberSecond) {
        return numberFirst + numberSecond;
    }
}
