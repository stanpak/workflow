package com.tribium.workflow.core;

import java.util.Date;
import java.util.UUID;

/**
 * Represents the singular step in the process.
 */
public class Step {
    public String id;

    public StepDefinition definition;

    public boolean started = false;
    public boolean completed = false;
    public Date startedAt;
    public Date completedAt;
    public Step() {
        id = UUID.randomUUID().toString();
    }
}
