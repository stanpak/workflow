package com.tribium.workflow.core;

@FunctionalInterface
public interface StepOperation {
    void execute(Process process, Step step, Object... input);
}
