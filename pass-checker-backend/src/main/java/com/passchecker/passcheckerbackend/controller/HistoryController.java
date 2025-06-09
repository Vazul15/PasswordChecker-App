package com.passchecker.passcheckerbackend.controller;

import com.passchecker.passcheckerbackend.model.dto.HistoryDTO;
import com.passchecker.passcheckerbackend.service.HistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/")
    public List<HistoryDTO> getHistory() {
        return historyService.getAllHistory();
    }
}
