package com.kaltner.calculator.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class History {
    private Long id;
    private final Action action;
    private final int operator1;
    private final int operator2;
    private final double result;
    private LocalDateTime timeStamp;
}
