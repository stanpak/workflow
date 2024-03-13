package com.tribium.workflow.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComplexScenarioTest {
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
    void complexProcess() {
        ProcessDefinition pd = new ScenarioPD();
        pd = processDefinitionRepository.create(pd);

        Process p = engine.startProcess(pd.id);

        ScenarioPD.CreateSubscriptionStep.Input i1 = new ScenarioPD.CreateSubscriptionStep.Input();
        i1.subscriptionId = "sub123Id";
        i1.subscriptionName = "sub123";
        i1.carId="car123";
        i1.caseId="case123";
        i1.location="US_EAST";
        i1.environment="E1";

        Step s = engine.completeStep(p.id, null, i1);
        s = engine.completeStep(p.id);
        s = engine.completeStep(p.id);

        // At this stage the process should conclude.
        // This step should not execute.
        s = engine.completeStep(p.id);
    }

    /**
     * This is implementation of our process definition.
     * Here we need to define the process in form of steps and transitions.
     */
    public static class ScenarioPD extends ProcessDefinition {
        public String subscriptionId;
        public String subscriptionName;

        public ScenarioPD() {
            super("Complex Scenario", Context::new);

            StepDefinition s1 = new CreateSubscriptionStep();
            addStepDefinition(s1);

            StepDefinition s2 = new CreateResourceGroupStep();
            addStepDefinition(s2);

            s1.addTransitionDefinition(new Transition("default",
                    (p, s) -> {
                        Context context = (Context) p.context;
                        return context.subscriptionId != null;
                    }, s2));

            StepDefinition s3 = new CreateNSGStep();
            addStepDefinition(s3);

            s2.addTransitionDefinition(new Transition("default",
                    (p, s) -> {
                        Context context = (Context) p.context;
                        return context.resourceGroupName != null;
                    }, s3));

            StepDefinition s4 = new ConfigureVNETStep();
            addStepDefinition(s4);

            s3.addTransitionDefinition(new Transition("default",
                    (p, s) -> {
                        Context context = (Context) p.context;
                        return context.nsgName != null;
                    }, s4));

            defaultStart = s1;
        }

        public static class Context extends Process.Context {
            public String carId;
            public String caseId;
            public String subscriptionId;
            public String subscriptionName;
            public String environment;
            public String location;
            public String resourceGroupName;
            public String nsgName;
            public String nsgDefinition;
        }

        public static class CreateSubscriptionStep extends StepDefinition implements StepOperation {
            public CreateSubscriptionStep() {
                super("Create Subscription", false, null);
                this.operation = this;
            }

            @Override
            public void execute(Process process, Step step, Object... input) {
                System.out.println("Creating the subscription manually...");
                Context context = (Context) process.context;
                Input i = (Input) input[0];
                context.carId = i.carId;
                context.caseId = i.caseId;
                context.subscriptionId = i.subscriptionId;
                context.subscriptionName = i.subscriptionName;
                context.environment = i.environment;
                context.location = i.location;
            }

            public static class Input extends StepDefinition.Input {
                public String carId;
                public String caseId;
                public String subscriptionId;
                public String subscriptionName;
                public String environment;
                public String location;
            }
        }

        public static class CreateResourceGroupStep extends StepDefinition implements StepOperation {
            public CreateResourceGroupStep() {
                super("Create Resource Group", true, null);
                this.operation = this;
            }

            @Override
            public void execute(Process process, Step step, Object... input) {
                System.out.println("Creating the resource group automatically...");
                Context context = (Context) process.context;
//                Input i = (Input) input[0];
                context.resourceGroupName = "<made resource group based on process context>";
            }

//            public static class Input extends StepDefinition.Input{
//            }
        }

        public static class CreateNSGStep extends StepDefinition implements StepOperation {
            public CreateNSGStep() {
                super("Create NSG", false, null);
                this.operation = this;
            }

            @Override
            public void execute(Process process, Step step, Object... input) {
                System.out.println("Creating the default NSG manually...");
                Context context = (Context) process.context;
                context.nsgName = "<made resource group based on process context>";
                context.nsgDefinition = "<default NSG>";
            }
        }

        public static class ConfigureVNETStep extends StepDefinition implements StepOperation {
            public ConfigureVNETStep() {
                super("Configure VNET", false, null);
                this.operation = this;
            }

            @Override
            public void execute(Process process, Step step, Object... input) {
                System.out.println("Configuring the VNET manually...");
            }
        }
    }

}