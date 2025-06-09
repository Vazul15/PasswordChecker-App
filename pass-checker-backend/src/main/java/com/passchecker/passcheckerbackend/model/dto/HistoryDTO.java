package com.passchecker.passcheckerbackend.model.dto;


import java.time.LocalDateTime;

public record HistoryDTO(String ipAddress, Integer port, String requestUri, String requestMethod, String queryParams, LocalDateTime timestamp, String localAddress, String userAgent) {
}
