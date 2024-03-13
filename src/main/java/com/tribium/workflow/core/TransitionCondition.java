package com.tribium.workflow.core;

@FunctionalInterface
public interface TransitionCondition {
    boolean evaluateOn(Process p, Step s);
}
