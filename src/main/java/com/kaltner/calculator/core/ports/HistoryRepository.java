package com.kaltner.calculator.core.ports;

import com.kaltner.calculator.core.domain.History;

import java.util.List;

public interface HistoryRepository {
    void save(History history);

    List<History> find();
}
