package com.passchecker.passcheckerbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer portNumber;

    private String ipAddress;

    private String requestUri;

    private String requestMethod;

    private String queryParams;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private String localAddress;

    private String userAgent;

    public History(Integer portNumber, String ipAddress, String requestUri, String requestMethod, String queryParams, LocalDateTime timestamp, String localAddress) {
        this.portNumber = portNumber;
        this.ipAddress = ipAddress;
        this.requestUri = requestUri;
        this.requestMethod = requestMethod;
        this.queryParams = queryParams;
        this.timestamp = timestamp;
        this.localAddress = localAddress;
    }
}
