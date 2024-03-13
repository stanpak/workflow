package com.tribium.workflow.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimpleScenarioTest {
    @Autowired
    private Engine engine;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;

    /**
     * In this scenario we create a process definition that will have two steps.
     */
    @Test
    void simpleProcess() {
        ProcessDefinition pd = new ScenarioPD();
        pd = processDefinitionRepository.create(pd);

        Process p = engine.startProcess(pd.id);

        Step s = engine.completeStep(p.id, null, "this is the first step");
        s = engine.completeStep(p.id, null, "this is the next step");
    }

    /**
     * This is implementation of our process definition.
     * Here we need to define the process in form of steps and transitions.
     */
    public static class ScenarioPD extends ProcessDefinition {
        public ScenarioPD() {
            super("Scenario 1", Process.Context::new);

            // Step 1

            StepDefinition nsd = new StepDefinition("second", false, (p, s, i) -> {
                System.out.println(i[0]);
            });
            addStepDefinition(nsd);

            // Step 2

            StepDefinition sd = new StepDefinition("first", false, (p, s, i) -> {
                System.out.println(i[0]);
            });

            // Add the default transition
            sd.addTransitionDefinition(new Transition("default",
                    (p, s) -> true, nsd));

            addStepDefinition(sd);

            defaultStart = sd;
        }
    }

}