package com.kaltner.calculator.core.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class History {
    private final Action action;
    private final int operator1;
    private final int operator2;
    private final double result;
    private final LocalDateTime timeStamp = LocalDateTime.now();
}
