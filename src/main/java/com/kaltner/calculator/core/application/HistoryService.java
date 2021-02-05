package com.kaltner.calculator.core.application;

import com.kaltner.calculator.core.domain.History;

import java.util.List;

public interface HistoryService {
    void save(History history);

    List<History> find();
}
