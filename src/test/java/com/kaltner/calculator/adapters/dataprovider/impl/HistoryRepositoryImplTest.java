package com.kaltner.calculator.adapters.dataprovider.impl;

import com.kaltner.calculator.adapters.dataprovider.entities.HistoryEntity;
import com.kaltner.calculator.adapters.dataprovider.repositories.H2HistoryRepository;
import com.kaltner.calculator.core.domain.Action;
import com.kaltner.calculator.core.domain.History;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class HistoryRepositoryImplTest {

    @MockBean
    H2HistoryRepository h2Repository;

    @Autowired
    HistoryRepositoryImpl historyRepository;

    @Test
    void save() {
        History history = new History(Action.ADD, 1, 1, 2);
        historyRepository.save(history);
        verify(h2Repository, Mockito.times(1)).save(any(HistoryEntity.class));
    }

    @Test
    void findAll() {
        HistoryEntity entity1 = new HistoryEntity(Action.ADD, 1, 1, 2);
        HistoryEntity entity2 = new HistoryEntity(Action.SUBTRACT, 1, 1, 2);
        List<HistoryEntity> entities = List.of(entity1,entity2);

        when(h2Repository.findAllByOrderByTimeStampDesc()).thenReturn(entities);

        List<History> histories = historyRepository.findAll();

        Assertions.assertEquals(entities.size(),histories.size());

        verify(h2Repository, Mockito.times(1)).findAllByOrderByTimeStampDesc();
    }

    @Test
    void findAllEmptyList() {

        when(h2Repository.findAllByOrderByTimeStampDesc()).thenReturn(List.of());

        List<History> histories = historyRepository.findAll();

        Assertions.assertEquals(Collections.EMPTY_LIST,histories);

        verify(h2Repository, Mockito.times(1)).findAllByOrderByTimeStampDesc();
    }
}
