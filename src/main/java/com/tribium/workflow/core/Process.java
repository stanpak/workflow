package com.tribium.workflow.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents the instance of the process.
 */
public class Process {
    public String id;

    /**
     * Current state of execution of this process in form of steps. They form a history of executions.
     * The most recent one is the last one in the list.
     */
    public List<Step> history = new ArrayList<>();

    public Step recentStep;

    /**
     * Is this process alive?
     */
    public boolean active = false;

    public Date startedAt;
    public Date concludedAt;
    public String definitionId;

    public String definitionName;

    public Context context;

    public static class Context{
    }

    public void setDefinition(ProcessDefinition pd){
        definitionId=pd.id;
        definitionName= pd.name;
        context = pd.contextFactory.create();
    }

    public void addToHistory(Step s) {
        history.add(s);
    }

    public Step findStep(String stepId) {
        if (recentStep != null && stepId.equals(recentStep.id))
            return recentStep;
        return null;
    }
}
