package com.tribium.workflow.tryout;

import com.tribium.workflow.core.Process;
import com.tribium.workflow.core.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This is a sample controller to test various simple scenarios that may happen in code
 * and to see how they will be handled.
 */
@RestController
@RequestMapping("/process")
public class ProcessController {
    @Autowired
    private ProcessRepository repository;

    @GetMapping("/findAll")
    public List<Process> findAll() {
        return repository.finAll();
    }

    @GetMapping("/findAllActive")
    public List<Process> findAllActive() {
        return repository.findAllActive();
    }
}
