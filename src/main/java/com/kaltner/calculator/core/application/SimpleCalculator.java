package com.kaltner.calculator.core.application;

import com.kaltner.calculator.core.domain.CalculatorOperationException;

public class SimpleCalculator implements Calculator{

    @Override
    public double add(int operator1, int operator2) {
        return (double) operator1 + operator2;
    }

    @Override
    public double subtract(int operator1, int operator2) {
        return (double) operator1 - operator2;
    }

    @Override
    public double multiply(int operator1, int operator2) {
        return (double) operator1 * operator2;
    }

    @Override
    public double divide(int operator1, int operator2) {
        final double result = (double) operator1 / operator2;
        if (Double.isNaN(result)) {
            throw new CalculatorOperationException();
        }
        return result;
    }
}
