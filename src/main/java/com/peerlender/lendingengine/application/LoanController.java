package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import com.peerlender.lendingengine.domain.service.LoanApplicationAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanApplicationAdapter loanApplicationAdapter;

    @Autowired
    public LoanController(LoanApplicationRepository loanApplicationRepository, UserRepository userRepository,
                          LoanApplicationAdapter loanApplicationAdapter) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanApplicationAdapter = loanApplicationAdapter;
    }

    @PostMapping(value = "/loan/request")
    public void requestLoan(@RequestBody final LoanRequest loanRequest) {
        loanApplicationRepository.save(loanApplicationAdapter.transform(loanRequest));
    }

    @GetMapping(value = "/users")
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/loan/requests")
    public List<LoanApplication> findAllLoanApplications() {
        return loanApplicationRepository.findAll();
    }

    @PostMapping(value = "/loan/accept/{lenderId}/{loanApplicationId}")
    public void acceptLoan(@PathVariable String lenderId, @PathVariable String loanApplicationId) {

    }

}
