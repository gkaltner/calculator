package com.kaltner.calculator.core.application;

import com.kaltner.calculator.core.domain.Action;
import com.kaltner.calculator.core.domain.CalculatorOperationException;
import com.kaltner.calculator.core.domain.History;

public class SimpleCalculator implements Calculator {

    private final HistoryService historyService;

    public SimpleCalculator(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public double add(int operator1, int operator2) {
        final double result = (double) operator1 + operator2;
        historyService.save(new History(Action.ADD, operator1, operator2, result));
        return result;
    }

    @Override
    public double subtract(int operator1, int operator2) {
        final double result = (double) operator1 - operator2;
        historyService.save(new History(Action.SUBTRACT, operator1, operator2, result));
        return result;
    }

    @Override
    public double multiply(int operator1, int operator2) {
        final double result = (double) operator1 * operator2;
        historyService.save(new History(Action.MULTIPLY, operator1, operator2, result));
        return result;
    }

    @Override
    public double divide(int operator1, int operator2) {
        final double result = (double) operator1 / operator2;
        historyService.save(new History(Action.DIVIDE, operator1, operator2, result));
        if (Double.isNaN(result)) {
            throw new CalculatorOperationException();
        }
        return result;
    }
}
