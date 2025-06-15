package com.passchecker.passcheckerbackend.service;

import com.passchecker.passcheckerbackend.model.dto.HistoryDTO;
import com.passchecker.passcheckerbackend.model.entity.History;
import com.passchecker.passcheckerbackend.repository.HistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * Service class responsible for managing history records.
 * Handles saving and retrieving request history data.
 */
@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    /**
     * Constructs the service with the provided {@link HistoryRepository}.
     *
     * @param historyRepository the repository used for storing and retrieving history records
     */
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    /**
     * Saves a history record to the database.
     *
     * @param history the {@link HistoryDTO} object containing request details
     */
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

    /**
     * Retrieves all stored history records.
     *
     * @return a list of {@link HistoryDTO} objects representing past requests
     */
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

    /**
     * Utility method to set a value if it is not null.
     *
     * @param setter the setter method reference
     * @param value the value to be set if it is not null
     * @param <T> the type of the value
     */
    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
