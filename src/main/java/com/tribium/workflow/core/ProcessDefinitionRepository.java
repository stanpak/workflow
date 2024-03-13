package com.tribium.workflow.core;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * This is a place where our process definitions are stored. In this case it is a class that keeps all the processes in memory.
 * Later it can be replaced by the database implementation.
 */
@Component
public class ProcessDefinitionRepository {
    private final Map<String, ProcessDefinition> objectMap = new HashMap<>();

    public ProcessDefinition findOne(String id) {
        return objectMap.get(id);
    }

    public List<ProcessDefinition> finAll() {
        return new ArrayList<>(objectMap.values());
    }

    public ProcessDefinition create(ProcessDefinition pd) {
        pd.id = UUID.randomUUID().toString();
        objectMap.put(pd.id, pd);
        return pd;
    }
}
