package com.passchecker.passcheckerbackend.service;

import com.passchecker.passcheckerbackend.model.dto.HistoryDTO;
import com.passchecker.passcheckerbackend.model.entity.History;
import com.passchecker.passcheckerbackend.repository.HistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;


@Service
public class HistoryService {
    HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }


    public void saveHistory(HistoryDTO history) {
        History historyEntity = new History();

        setIfNotNull(historyEntity::setPortNumber, history.port());
        setIfNotNull(historyEntity::setIpAddress, history.ipAddress());
        setIfNotNull(historyEntity::setRequestUri, history.requestUri());
        setIfNotNull(historyEntity::setRequestMethod, history.requestMethod());
        setIfNotNull(historyEntity::setQueryParams, history.queryParams());
        setIfNotNull(historyEntity::setTimestamp, history.timestamp());
        setIfNotNull(historyEntity::setLocalAddress, history.localAddress());
        setIfNotNull(historyEntity::setUserAgent, history.userAgent());

        historyRepository.save(historyEntity);
    }

    public List<HistoryDTO> getAllHistory() {
        return historyRepository.findAll().stream()
                .map(history ->
                    new HistoryDTO(
                            history.getIpAddress(),
                            history.getPortNumber(),
                            history.getRequestUri(),
                            history.getRequestMethod(),
                            history.getQueryParams(),
                            history.getTimestamp(),
                            history.getLocalAddress(),
                            history.getUserAgent()
                            )
                ).toList();
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
