package com.kaltner.calculator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaltner.calculator.adapters.entrypoints.request.RequestOperation;
import com.kaltner.calculator.core.domain.Action;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SimpleCalculatorIntegrationTest {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @CsvSource({
            "ADD,1,1,2", "ADD,-2,1,-1",
            "SUBTRACT,3,1,2", "SUBTRACT,-3,1,-4",
            "MULTIPLY,2,3,6", "MULTIPLY,0,3,0", "MULTIPLY,-1,3,-3",
            "DIVIDE,4,2,2", "DIVIDE,0,2,0", "DIVIDE,-1,4,-0.25"
    })
    void calculatorTest(Action action, int operator1, int operator2, double resultExpected) throws Exception {

        RequestOperation operation = new RequestOperation(action, operator1, operator2);

        mvc.perform(post("/calculator")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(operation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(resultExpected)));
    }

    @ParameterizedTest
    @CsvSource({
            "DIVIDE,0,0", "DIVIDE,2,0,",
    })
    void calculatorFailTest(Action action, int operator1, int operator2) throws Exception {

        RequestOperation operation = new RequestOperation(action, operator1, operator2);

        mvc.perform(post("/calculator")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(operation)))
                .andExpect(status().isBadRequest());
    }
}
