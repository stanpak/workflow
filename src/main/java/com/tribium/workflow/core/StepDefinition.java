package com.tribium.workflow.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StepDefinition {
    public String id;
    public String name;

    public StepDefinition(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public StepDefinition(String name, boolean automated, StepOperation operation){
        this(name);
        this.automated = automated;
        this.operation = operation;
    }

    public List<Transition> transitions = new ArrayList<>();
    /**
     * This is the actual operation that needs to be implemented.
     */
    public StepOperation operation;

    public boolean automated = false;

    public void addTransitionDefinition(Transition td) {
        transitions.add(td);
    }

    public static class Input {
    }
}
