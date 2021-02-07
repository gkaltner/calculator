package com.kaltner.calculator.adapters.entrypoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaltner.calculator.adapters.entrypoints.request.RequestOperation;
import com.kaltner.calculator.core.application.Calculator;
import com.kaltner.calculator.core.domain.Action;
import com.kaltner.calculator.core.domain.CalculatorOperationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    Calculator calculator;

    @Autowired
    private MockMvc mvc;

    @Test
    void executeAddOperation() throws Exception {
        final int operator1 = 1;
        final int operator2 = 1;
        final double result = 2;

        RequestOperation operation = new RequestOperation(Action.ADD, operator1, operator2);

        when(calculator.add(operator1, operator2)).thenReturn(result);

        mvc.perform(post("/calculator")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(operation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(result)));

        verify(calculator, times(1)).add(operator1, operator2);
    }

    @Test
    void executeSubtractOperation() throws Exception {
        final int operator1 = 4;
        final int operator2 = 3;
        final double result = 1;

        RequestOperation operation = new RequestOperation(Action.SUBTRACT, operator1, operator2);

        when(calculator.subtract(operator1, operator2)).thenReturn(result);

        mvc.perform(post("/calculator")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(operation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(result)));

        verify(calculator, times(1)).subtract(operator1, operator2);
    }

    @Test
    void executeMultiplyOperation() throws Exception {
        final int operator1 = 3;
        final int operator2 = 3;
        final double result = 9;

        RequestOperation operation = new RequestOperation(Action.MULTIPLY, operator1, operator2);

        when(calculator.multiply(operator1, operator2)).thenReturn(result);

        mvc.perform(post("/calculator")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(operation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(result)));

        verify(calculator, times(1)).multiply(operator1, operator2);
    }

    @Test
    void executeDivideOperation() throws Exception {
        final int operator1 = 3;
        final int operator2 = 3;
        final double result = 1;

        RequestOperation operation = new RequestOperation(Action.DIVIDE, operator1, operator2);

        when(calculator.divide(operator1, operator2)).thenReturn(result);

        mvc.perform(post("/calculator")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(operation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(result)));

        verify(calculator, times(1)).divide(operator1, operator2);
    }

    @Test
    void executeFAILDivideOperation() throws Exception {
        final int operator1 = 0;
        final int operator2 = 0;

        RequestOperation operation = new RequestOperation(Action.DIVIDE, operator1, operator2);

        when(calculator.divide(operator1, operator2)).thenThrow(CalculatorOperationException.class);

        mvc.perform(post("/calculator")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(operation)))
                .andExpect(status().isBadRequest());

        verify(calculator, times(1)).divide(operator1, operator2);
    }
}
