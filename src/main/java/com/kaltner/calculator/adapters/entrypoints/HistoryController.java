package com.kaltner.calculator.adapters.entrypoints;

import com.kaltner.calculator.core.application.HistoryService;
import com.kaltner.calculator.core.domain.History;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @CrossOrigin
    @GetMapping
    public List<History> findHistory() {
        return historyService.find();
    }
}
