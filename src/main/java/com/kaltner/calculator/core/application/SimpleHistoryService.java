package com.kaltner.calculator.core.application;

import com.kaltner.calculator.core.domain.History;
import com.kaltner.calculator.core.ports.HistoryRepository;

import java.util.List;

public class SimpleHistoryService implements HistoryService {

    private final HistoryRepository historyRepository;

    public SimpleHistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void save(History history) {
        historyRepository.save(history);
    }

    @Override
    public List<History> find() {
        return historyRepository.findAll();
    }
}
