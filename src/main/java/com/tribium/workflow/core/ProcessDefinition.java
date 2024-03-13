package com.tribium.workflow.core;

import java.util.ArrayList;
import java.util.List;

public class ProcessDefinition {
    public ProcessDefinition(String name, ContextFactory contextFactory){
        this.name=name;
        this.contextFactory = contextFactory;
    }

    public String id;

    public ContextFactory contextFactory;

    @FunctionalInterface
    public interface ContextFactory{
        Process.Context create();
    }

    public List<StepDefinition> stepDefinitions = new ArrayList<>();
    /**
     * Starting step of the process
     */
    public StepDefinition defaultStart;
    public String name;
    public String description;

    public void addStepDefinition(StepDefinition sd) {
        stepDefinitions.add(sd);
    }
}
