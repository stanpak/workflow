package com.tribium.workflow.core;

public class Transition {

    public Transition(String name, TransitionCondition condition,StepDefinition transitionTo){
        this.name = name;
        this.condition = condition;
        this.transitionTo = transitionTo;
    }

    public String name;

    /**
     * Condition that checks whether the transition can be made.
     */
    public TransitionCondition condition;

    /**
     * What is the next step if the condition is satisfied?
     */
    public StepDefinition transitionTo;
}
