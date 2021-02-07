package com.kaltner.calculator.infraestructure;

import com.kaltner.calculator.adapters.dataprovider.impl.HistoryRepositoryImpl;
import com.kaltner.calculator.adapters.dataprovider.repositories.H2HistoryRepository;
import com.kaltner.calculator.core.application.Calculator;
import com.kaltner.calculator.core.application.HistoryService;
import com.kaltner.calculator.core.application.SimpleCalculator;
import com.kaltner.calculator.core.application.SimpleHistoryService;
import com.kaltner.calculator.core.ports.HistoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.kaltner.calculator.adapters.dataprovider.repositories")
public class SpringConfig {

    private final H2HistoryRepository repository;

    public SpringConfig(H2HistoryRepository repository) {
        this.repository = repository;
    }

    @Bean
    public HistoryRepository historyRepository() {
        return new HistoryRepositoryImpl(repository);
    }

    @Bean
    public HistoryService historyService() {
        return new SimpleHistoryService(historyRepository());
    }

    @Bean
    public Calculator calculator() {
        return new SimpleCalculator(historyService());
    }
}
