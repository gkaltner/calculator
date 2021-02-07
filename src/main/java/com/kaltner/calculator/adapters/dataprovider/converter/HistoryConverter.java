package com.kaltner.calculator.adapters.dataprovider.converter;

import com.kaltner.calculator.adapters.dataprovider.entities.HistoryEntity;
import com.kaltner.calculator.core.domain.History;

public class HistoryConverter {

    private HistoryConverter(){}

    public static HistoryEntity convert(History history) {
        return new HistoryEntity(history.getId(),
                history.getAction(),
                history.getOperator1(),
                history.getOperator2(),
                history.getResult(),
                history.getTimeStamp());
    }

    public static History convert(HistoryEntity historyEntity) {
        return new History(historyEntity.getId(),
                historyEntity.getAction(),
                historyEntity.getOperator1(),
                historyEntity.getOperator2(),
                historyEntity.getResult(),
                historyEntity.getTimeStamp());
    }
}
