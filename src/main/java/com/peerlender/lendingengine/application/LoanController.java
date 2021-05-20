package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.LoanRequestRepository;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoanController {

    private final LoanRequestRepository loanRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanController(LoanRequestRepository loanRequestRepository, UserRepository userRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/loan/request")
    public void requestLoan(@RequestBody final LoanRequest loanRequest) {
        System.out.println(loanRequest);
    }

    @GetMapping(value = "/users")
    public List<User> findUsers() {
        return userRepository.findAll();
    }

}
