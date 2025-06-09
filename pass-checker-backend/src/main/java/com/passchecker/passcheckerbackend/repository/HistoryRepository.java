package com.passchecker.passcheckerbackend.repository;

import com.passchecker.passcheckerbackend.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
