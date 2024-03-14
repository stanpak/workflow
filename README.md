# Workflow Framework

Preliminary version of the workflow framework. This repository is used for the development of the PoC and not the final
product.

## Features

* **Working in memory or in permanent store**

  As the primary method the workflows can be hosted entirely in memory. However, upon the choice they can be persisted in the object database, like MongoDB. 


* **Multiple process definitions**

  We can keep any number of defined processes. Each process definition has its own set of steps.
  Process definitions would be statically defined (in code) the steps would need to have provided implementation to work
  automatically, otherwise they would be treated as manual.

  Each step would have its definition in form of a class. If that class was not implemented it would be treated as
  manual. If implemented the implementation would be triggered upon execution phase of the step.


* **Process pre-conditions**

  Each of them can be applicable at specific preconditions (for example the defining the assets is possible only after
  the deployment environment was set up.)

  This feature can be called as process dependency.

  Each process can have a functional (or an implemented method that do all the checks on other processes to determine if
  it is ready to start.)


* **Decoupled process from the step implementation.**

  PROs:

    * This way we can separate the processes from the implementation of each step. The framework and the processes
      do not need to be dependent on implementation.

    * Step implementation can go in parallel and done by different teams/parties in different pace.


* **Steps can be systematic or manual.**

  If the systematic process is not provided we assume the manual is used.

  PROs:

  We do not have all working as automated. We can implement and test whole process as manual.

  We can write test scenarios and do tests independent on their implementation.


* **Primary interface is the REST API**

  PROs: This way we can use it for testing and actually provide the implementation for our clients. If no implementation
  provided then the step would work as if it was manual process: the client would need to push it forward by providing
  additional information (results) that can be used in next steps.


* **Decoupled UI**

  The UI can be implemented later after testing the implementation. UI would be likely be needed to be developed
  separately for each step. This also can be done independently so even if the UI is not complete the general processes
  can work fine from the API perspective.


* **Clear process metrics**

  We can expose anything that it can be known about the process itself, like the completion, time, ETA can be
  approximated etc. The user can query about the state of their case.


* **Parallel processing**

  Kind of obvious thing. Multiple cases (aka process instances) can be working on in parallel in complete separation.


* **Process execution history**

  Any process history would be easily preserved and could be used for context, or introspection. Audit trail can be
  useful thing.

## Process Definition

Below is very simplified idea of the process definition with couple of steps, with transitions. 
If there is just one transition it is called a default one. That means that as soon the first step is completed the process 
transitions automatically to the next step of the process.
If there is multiple transitions originating from a singular step, then they are based on the condition.

```text
step --- step --(transition)--> step ---(transitionA)--> step 
                                     \--(transitionB)--> step
```

In this stage of implementation of the workflow framework, the process may transition to just one step in the process. However, later we may add a functionality of splitting the
process into two or more steps (threads) which can later join in order to make sure that the whole goal of the process has accomplished.

When the process starts its instance is created.

All the properties of the process can reside in the process context (the process instance itself).

When each step is invoked to be executed (`executeStep()` method) the input is provided to a step method.

