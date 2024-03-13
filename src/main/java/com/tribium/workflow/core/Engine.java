package com.tribium.workflow.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class Engine {
    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;

    /**
     * TODO: Add code to spawn this as a separate thread.
     *
     * @param pDefId
     * @return
     */
    public Process startProcess(String pDefId) {
        ProcessDefinition pd = processDefinitionRepository.findOne(pDefId);
        if (pd == null)
            throw new RuntimeException("Process not found!");

        // Create new instance of a process...
        Process p = new Process();
        p.setDefinition(pd);
        p.id = UUID.randomUUID().toString();
        p.startedAt = new Date();

        // Then start it by transitioning the process to the first step...
        if(pd.defaultStart ==null)
            throw new RuntimeException("The first step should be provided in process definition.");
        StepDefinition sd = pd.defaultStart;
        transitionTo(p,sd);

        processRepository.create(p);
        return p;
    }

    public Step completeAutomatedStep(Process p) {
        return completeStep(p,null);
    }

    private Step completeStep(Process p, String stepId, Object... input) {
        if(p.concludedAt!=null){
            System.out.println("This process has concluded so we cannot execute any further.");
            return p.recentStep;
        }

        Step s = (stepId!=null)?p.findStep(stepId):p.recentStep;
        if(s==null)
            throw new RuntimeException("Step not identified.");

        if(s.definition.operation==null)
            throw new RuntimeException("The operation of the step must be defined in the step definition.");

        System.out.println("Completing the step '"+s.definition.name+"'...");

        // TODO add code checking for success...
        s.definition.operation.execute(p,s, input);
        s.completed = true;
        s.completedAt = new Date();

        if(s.definition.transitions.isEmpty())
        {
            // TODO This is the last step. If concluded successfully that means it is the end of the process.
            p.active = false;
            p.concludedAt = new Date();

            System.out.println("This is last step. The process assumes completion.");
            return s;
        }

        // If there was success  -> transition the step...
        for(Transition td: s.definition.transitions)
            if(td.condition.evaluateOn(p,s))
            {
                StepDefinition sd = td.transitionTo;
                System.out.println("Transitioning to new step ("+td.name+") -> '"+sd.name+"'.");
                s = transitionTo(p,sd);
                return s;
            }

        // TODO If it comes to this place that means the process cannot proceed to the next transition.

        return s;
    }

    /**
     * Attempt to complete the step by providing any additional required information.
     *
     * @param processId
     * @param stepId
     */
    public Step completeStep(String processId, String stepId, Object... input) {
        Process p = processRepository.findOne(processId);
        if (p == null)
            throw new RuntimeException("No process found for this ID.");

        return completeStep(p,stepId,input);
    }

    public Step completeStep(String processId) {
        return completeStep(processId,null);
    }

    private Step transitionTo(Process p, StepDefinition sd) {
        Step s = new Step();
        s.definition = sd;
        s.started = true;
        s.startedAt = p.startedAt;
        p.recentStep = s;

        p.addToHistory(s);

        if(sd.automated){
            System.out.println("This step is automated. Executing the step without manual intervention.");
            p.recentStep = completeAutomatedStep(p);
        }
        else
            System.out.println("This step is manual, therefore it must be completed by the client.");

        return s;
    }
}
