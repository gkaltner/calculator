package com.kaltner.calculator.core.application;

import com.kaltner.calculator.core.domain.CalculatorOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleCalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setup() {
        calculator = new SimpleCalculator();
    }

    @Test
    void add() {
        final double result = calculator.add(1,1);
        Assertions.assertEquals(2,result);
    }

    @Test
    void addWhenAnOperatorIsNegative() {
        final double result = calculator.add(-2,1);
        Assertions.assertEquals(-1,result);
    }

    @Test
    void subtract() {
        final double result = calculator.subtract(3,1);
        Assertions.assertEquals(2,result);
    }

    @Test
    void subtractWhenAnOperatorIsNegative() {
        final double result = calculator.subtract(-3,1);
        Assertions.assertEquals(-4,result);
    }

    @Test
    void multiplyWhen() {
        final double result = calculator.multiply(2,3);
        Assertions.assertEquals(6,result);
    }

    @Test
    void multiplyWhenAnOperatorIsZero() {
        final double result = calculator.multiply(0,3);
        Assertions.assertEquals(0,result);
    }

    @Test
    void multiplyWhenAnOperatorIsNegative() {
        final double result = calculator.multiply(-1,3);
        Assertions.assertEquals(-3,result);
    }

    @Test
    void divide() {
        final double result = calculator.divide(4,2);
        Assertions.assertEquals(2,result);
    }

    @Test
    void divideWhenOperator1IsZero() {
        final double result = calculator.divide(0,2);
        Assertions.assertEquals(0,result);
    }

    @Test
    void divideWhenOperator2IsZero() {
        Assertions.assertThrows(CalculatorOperationException.class,() -> {
            calculator.divide(0,0);
        });
    }
}
