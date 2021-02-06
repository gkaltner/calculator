package com.kaltner.calculator.infraestructure;

import com.kaltner.calculator.core.application.Calculator;
import com.kaltner.calculator.core.application.HistoryService;
import com.kaltner.calculator.core.application.SimpleCalculator;
import com.kaltner.calculator.core.application.SimpleHistoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public HistoryService historyService() {
        return new SimpleHistoryService(null);
    }

    @Bean
    public Calculator calculator() {
        return new SimpleCalculator(historyService());
    }
}
