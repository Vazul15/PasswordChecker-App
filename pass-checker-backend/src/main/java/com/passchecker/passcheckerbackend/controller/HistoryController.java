package com.passchecker.passcheckerbackend.controller;

import com.passchecker.passcheckerbackend.model.dto.HistoryDTO;
import com.passchecker.passcheckerbackend.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * REST controller that manages history-related operations.
 * Provides endpoints for retrieving history records.
 */
@RestController
@RequestMapping("/api/history")
public class HistoryController {
    HistoryService historyService;

    /**
     * Constructs the controller with the provided {@link HistoryService}.
     *
     * @param historyService the service handling history data retrieval
     */
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * Retrieves all history records.
     *
     * @return a list of {@link HistoryDTO} objects representing stored history data
     */
    @GetMapping("/")
    public List<HistoryDTO> getHistory() {
        return historyService.getAllHistory();
    }

    /**
     * Simple test endpoint that returns a greeting message.
     *
     * @return a {@link ResponseEntity} containing "Hello World"
     */
    @GetMapping("/hi")
    public ResponseEntity getHello() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello World");
    }
}
