# Workflow Framework

Preliminary version of the workflow framework. This repository is used for the development of the PoC and not the final product.

## Features

## Process Definition

```text

    step --- step --(transition)--> step ---(transitionA)--> step 
                                         \--(transitionB)--> step

```

When the process starts its instance is created.

All of the properties of the process can reside in the process context (the process instance itself).

When each step is invoked to be executed (`executeStep()` method) the input is provided to a step method.