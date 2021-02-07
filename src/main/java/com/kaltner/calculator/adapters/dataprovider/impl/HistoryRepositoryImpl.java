package com.kaltner.calculator.adapters.dataprovider.impl;

import com.kaltner.calculator.adapters.dataprovider.converter.HistoryConverter;
import com.kaltner.calculator.adapters.dataprovider.repositories.H2HistoryRepository;
import com.kaltner.calculator.core.domain.History;
import com.kaltner.calculator.core.ports.HistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HistoryRepositoryImpl implements HistoryRepository {

    private final H2HistoryRepository repository;

    public HistoryRepositoryImpl(H2HistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(History history) {
        repository.save(HistoryConverter.convert(history));
    }

    @Override
    public List<History> findAll() {
        return repository.findAllByOrderByTimeStampDesc().stream()
                .map(HistoryConverter::convert)
                .collect(Collectors.toList());
    }
}
