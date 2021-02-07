package com.kaltner.calculator.core.application;

import com.kaltner.calculator.core.domain.Action;
import com.kaltner.calculator.core.domain.History;
import com.kaltner.calculator.core.ports.HistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class SimpleHistoryServiceTest {

    @InjectMocks
    SimpleHistoryService historyService;

    @Mock
    HistoryRepository historyRepository;

    History history;

    @BeforeEach
    void setup() {
        history = new History(Action.ADD, 1, 1, 2);
    }

    @Test
    void save() {
        historyService.save(history);
        Mockito.verify(historyRepository, Mockito.times(1)).save(history);
    }

    @Test
    void find() {
        List<History> histories = List.of(history);
        Mockito.when(historyRepository.findAll()).thenReturn(histories);

        List<History> result = historyService.find();

        Assertions.assertEquals(histories, result);
        Mockito.verify(historyRepository, Mockito.times(1)).findAll();
    }
}
