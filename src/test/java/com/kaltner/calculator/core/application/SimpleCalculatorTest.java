package com.kaltner.calculator.core.application;

import com.kaltner.calculator.core.domain.Action;
import com.kaltner.calculator.core.domain.CalculatorOperationException;
import com.kaltner.calculator.core.domain.History;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SimpleCalculatorTest {

    @Mock
    HistoryService historyService;

    @InjectMocks
    SimpleCalculator calculator;

    @Captor
    ArgumentCaptor<History> historyCaptor;

    @ParameterizedTest
    @CsvSource({"1,1,2", "-2,1,-1"})
    void add(int operator1, int operator2, double resultExpected) {
        final double result = calculator.add(operator1, operator2);

        Assertions.assertEquals(resultExpected, result);
        checkHistoryCall(Action.ADD, operator1, operator2, resultExpected);
    }

    @ParameterizedTest
    @CsvSource({"3,1,2", "-3,1,-4"})
    void subtract(int operator1, int operator2, double resultExpected) {
        final double result = calculator.subtract(operator1, operator2);
        Assertions.assertEquals(resultExpected, result);
        checkHistoryCall(Action.SUBTRACT, operator1, operator2, resultExpected);
    }

    @ParameterizedTest
    @CsvSource({"2,3,6", "0,3,0", "-1,3,-3"})
    void multiplyWhen(int operator1, int operator2, double resultExpected) {
        final double result = calculator.multiply(operator1, operator2);

        Assertions.assertEquals(resultExpected, result);
        checkHistoryCall(Action.MULTIPLY, operator1, operator2, resultExpected);
    }

    @ParameterizedTest
    @CsvSource({"4,2,2", "0,2,0", "-1,4,-0.25"})
    void divide(int operator1, int operator2, double resultExpected) {
        final double result = calculator.divide(operator1, operator2);

        Assertions.assertEquals(resultExpected, result);
        checkHistoryCall(Action.DIVIDE, operator1, operator2, resultExpected);
    }

    @ParameterizedTest
    @CsvSource({ "2,0,NaN", "0,0,NaN"})
    void divideWhenOperator2IsZero(int operator1, int operator2, double resultExpected) {
        Assertions.assertThrows(CalculatorOperationException.class,
                () -> calculator.divide(operator1, operator2));
        checkHistoryCall(Action.DIVIDE, operator1, operator2, resultExpected);
    }

    private void checkHistoryCall(Action action, int operator1, int operator2, double resultExpected) {
        Mockito.verify(historyService, Mockito.times(1)).save(historyCaptor.capture());
        History history = historyCaptor.getValue();
        Assertions.assertEquals(action, history.getAction());
        Assertions.assertEquals(operator1, history.getOperator1());
        Assertions.assertEquals(operator2, history.getOperator2());
        Assertions.assertEquals(resultExpected, history.getResult());
    }
}
