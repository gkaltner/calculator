package com.kaltner.calculator.adapters.dataprovider.entities;

import com.kaltner.calculator.core.domain.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HistoryEntity {
    private @Id
    @GeneratedValue
    Long id;
    private Action action;
    private int operator1;
    private int operator2;
    private double result;
    private LocalDateTime timeStamp = LocalDateTime.now();

    public HistoryEntity(Action action, int operator1, int operator2, int result) {
        this.action = action;
        this.operator1 = operator1;
        this.operator2 = operator2;
        this.result = result;
    }
}
