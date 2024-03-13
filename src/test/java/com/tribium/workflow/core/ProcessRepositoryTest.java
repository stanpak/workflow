package com.tribium.workflow.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProcessRepositoryTest {
    @Autowired
    private ProcessRepository repository;

    @Test
    void findOne() {
    }

    @Test
    void finAll() {
        List<Process> processes = repository.finAll();
        assertNotNull(processes);
    }

    @Test
    void findAllActive() {
        List<Process> processes = repository.findAllActive();
        assertNotNull(processes);
    }
}