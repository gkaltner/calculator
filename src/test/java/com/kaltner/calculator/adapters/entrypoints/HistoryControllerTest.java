package com.kaltner.calculator.adapters.entrypoints;

import com.kaltner.calculator.adapters.entrypoints.request.RequestOperation;
import com.kaltner.calculator.core.application.Calculator;
import com.kaltner.calculator.core.application.HistoryService;
import com.kaltner.calculator.core.domain.Action;
import com.kaltner.calculator.core.domain.CalculatorOperationException;
import com.kaltner.calculator.core.domain.History;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HistoryController.class)
class HistoryControllerTest {

    @MockBean
    HistoryService historyService;

    @Autowired
    private MockMvc mvc;

    @Test
    void findHistory() throws Exception {
        List<History> histories = List.of(new History(Action.ADD,1,1,2),new History(Action.ADD,2,2,4));
        when(historyService.find()).thenReturn(histories);

        mvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));;
    }

    @Test
    void findHistoryEmpty() throws Exception {
        List<History> histories = List.of();
        when(historyService.find()).thenReturn(histories);

        mvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));;
    }
}
