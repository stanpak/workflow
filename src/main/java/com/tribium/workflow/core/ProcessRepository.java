package com.tribium.workflow.core;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is a place where our processes are stored. In this case it is a class that keeps all the processes in memory.
 * Later it can be replaced by the database implementation.
 */
@Component
public class ProcessRepository {
    private final Map<String, Process> processMap = new HashMap<>();

    public Process findOne(String id) {
        return processMap.get(id);
    }

    public List<Process> finAll() {
        return new ArrayList<>(processMap.values());
    }

    public List<Process> findAllActive() {
        return processMap.values().stream()
                .filter(e -> e.active)
                .collect(Collectors.toList());
    }

    public Process create(Process p) {
        p.id = UUID.randomUUID().toString();
        processMap.put(p.id,p);
        return p;
    }
}
