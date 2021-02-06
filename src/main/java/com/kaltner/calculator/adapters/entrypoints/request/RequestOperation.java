package com.kaltner.calculator.adapters.entrypoints.request;

import com.kaltner.calculator.core.domain.Action;
import lombok.Data;

@Data
public class RequestOperation {
    private final Action action;
    private final int operator1;
    private final int operator2;
}
