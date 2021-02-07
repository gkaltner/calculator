package com.kaltner.calculator.adapters.dataprovider.repositories;

import com.kaltner.calculator.adapters.dataprovider.entities.HistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface H2HistoryRepository extends CrudRepository<HistoryEntity, Long> {
    List<HistoryEntity> findAllByOrderByTimeStampDesc();
}
