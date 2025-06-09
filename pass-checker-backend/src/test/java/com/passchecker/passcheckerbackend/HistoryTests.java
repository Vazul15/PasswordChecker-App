package com.passchecker.passcheckerbackend;

import com.passchecker.passcheckerbackend.model.dto.HistoryDTO;
import com.passchecker.passcheckerbackend.model.entity.History;
import com.passchecker.passcheckerbackend.repository.HistoryRepository;
import com.passchecker.passcheckerbackend.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryTests {

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryService historyService;

    @Test
    void testSaveHistoryMethodIsRunning() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        HistoryDTO historyDTO = new HistoryDTO(
                "172.19.0.1",
                8080,
                "/api/history/",
                "GET",
                null,
                LocalDateTime.parse("2025-06-08T18:21:38.288", formatter),
                "172.19.0.4",
                "PostmanRuntime/7.44.0"
        );

        History historyEntity = new History();

        when(historyRepository.save(any(History.class))).thenReturn(historyEntity);
        assertDoesNotThrow(() -> historyService.saveHistory(historyDTO));
        verify(historyRepository, times(1)).save(any(History.class));
    }
    @Test
    void testGetAllHistoryWithOneHistory() {

        History historyEntity = new History();
        historyEntity.setIpAddress("192.168.1.1");
        historyEntity.setPortNumber(8080);
        historyEntity.setRequestUri("/api/test");
        historyEntity.setRequestMethod("GET");
        historyEntity.setQueryParams("id=123");
        historyEntity.setTimestamp(LocalDateTime.now());
        historyEntity.setLocalAddress("192.168.1.100");
        historyEntity.setUserAgent("Mozilla/5.0");

        when(historyRepository.findAll()).thenReturn(List.of(historyEntity));

        List<HistoryDTO> result = historyService.getAllHistory();

        assertFalse(result.isEmpty());

        HistoryDTO dto = result.get(0);
        assertEquals(historyEntity.getIpAddress(), dto.ipAddress());
        assertEquals(historyEntity.getPortNumber(), dto.port());
        assertEquals(historyEntity.getRequestUri(), dto.requestUri());
        assertEquals(historyEntity.getRequestMethod(), dto.requestMethod());
        assertEquals(historyEntity.getQueryParams(), dto.queryParams());
        assertEquals(historyEntity.getTimestamp(), dto.timestamp());
        assertEquals(historyEntity.getLocalAddress(), dto.localAddress());
        assertEquals(historyEntity.getUserAgent(), dto.userAgent());

        verify(historyRepository, times(1)).findAll();
    }

    @Test
    void testGetAllHistoryWithMultipleHistorys() {
        History historyEntity = new History();
        historyEntity.setIpAddress("192.168.1.1");
        historyEntity.setPortNumber(8080);
        historyEntity.setRequestUri("/api/test");
        historyEntity.setRequestMethod("GET");
        historyEntity.setQueryParams("id=123");
        historyEntity.setTimestamp(LocalDateTime.now());
        historyEntity.setLocalAddress("192.168.1.100");
        historyEntity.setUserAgent("Mozilla/5.0");

        History historyEntity2 = new History();
        historyEntity2.setIpAddress("192.168.1.1");
        historyEntity2.setPortNumber(8080);
        historyEntity2.setRequestUri("/api/test");
        historyEntity2.setRequestMethod("GET");
        historyEntity2.setQueryParams(null);
        historyEntity2.setTimestamp(LocalDateTime.now());
        historyEntity2.setLocalAddress("192.168.1.100");
        historyEntity2.setUserAgent("PostmanRuntime/7.44.0");

        when(historyRepository.findAll()).thenReturn(List.of(historyEntity, historyEntity2));
        List<HistoryDTO> result = historyService.getAllHistory();
        assertFalse(result.isEmpty());
        HistoryDTO dto = result.get(1);
        assertEquals(historyEntity2.getIpAddress(), dto.ipAddress());
        assertEquals(historyEntity2.getPortNumber(), dto.port());
        assertEquals(historyEntity2.getRequestUri(), dto.requestUri());
        assertEquals(historyEntity2.getRequestMethod(), dto.requestMethod());
        assertEquals(historyEntity2.getQueryParams(), dto.queryParams());
        assertEquals(historyEntity2.getTimestamp(), dto.timestamp());
        assertEquals(historyEntity2.getLocalAddress(), dto.localAddress());
        assertEquals(historyEntity2.getUserAgent(), dto.userAgent());
        verify(historyRepository, times(1)).findAll();

    }

    @Test
    void testSaveHistoryWithDTO() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        HistoryDTO historyDTO = new HistoryDTO(
                "172.19.0.1",
                8080,
                "/api/history/",
                "GET",
                null,
                LocalDateTime.parse("2025-06-08T18:21:38.288", formatter),
                "172.19.0.4",
                "PostmanRuntime/7.44.0"
        );

        when(historyRepository.save(any(History.class))).thenAnswer(invocation -> {
            History savedEntity = invocation.getArgument(0);
            return savedEntity;
        });

        assertDoesNotThrow(() -> historyService.saveHistory(historyDTO));

        verify(historyRepository).save(argThat(history ->
                history.getIpAddress().equals(historyDTO.ipAddress()) &&
                        history.getPortNumber().equals(historyDTO.port()) &&
                        history.getRequestUri().equals(historyDTO.requestUri()) &&
                        history.getRequestMethod().equals(historyDTO.requestMethod()) &&
                        history.getTimestamp().equals(historyDTO.timestamp()) &&
                        history.getLocalAddress().equals(historyDTO.localAddress()) &&
                        history.getUserAgent().equals(historyDTO.userAgent())
        ));
    }

}
