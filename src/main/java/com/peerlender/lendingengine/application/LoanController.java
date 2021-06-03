package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.application.service.TokenValidationService;
import com.peerlender.lendingengine.domain.model.Loan;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerlender.lendingengine.domain.service.LoanApplicationAdapter;
import com.peerlender.lendingengine.domain.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;
    private final TokenValidationService tokenValidationService;

    @Autowired
    public LoanController(LoanApplicationRepository loanApplicationRepository,
                          LoanApplicationAdapter loanApplicationAdapter,
                          LoanService loanService,
                          TokenValidationService tokenValidationService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanApplicationAdapter = loanApplicationAdapter;
        this.loanService = loanService;
        this.tokenValidationService = tokenValidationService;
    }

    @PostMapping(value = "/loan/request")
    public void requestLoan(@RequestBody final LoanRequest loanRequest, @RequestHeader String authorization) {
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        loanApplicationRepository.save(loanApplicationAdapter.transform(loanRequest, borrower));
    }

    @GetMapping(value = "/loan/requests")
    public List<LoanApplication> findAllLoanApplications(@RequestHeader String authorization) {
        tokenValidationService.validateTokenAndGetUser(authorization);
        return loanApplicationRepository.findAll();
    }

    @PostMapping(value = "/loan/accept/{loanApplicationId}")
    public void acceptLoan(@PathVariable String loanApplicationId, @RequestHeader String authorization) {
        User lender = tokenValidationService.validateTokenAndGetUser(authorization);
        loanService.acceptLoan(lender.getUsername(),Long.parseLong(loanApplicationId));
    }

    @GetMapping(value = "/loans")
    public List<Loan> findAllLoans(@RequestHeader String authorization) {
        tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.getLoans();
    }

    @GetMapping(value = "/loan/borrowed")
    public List<Loan> findBorrowedLoans(@RequestHeader String authorization) {
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllBorrowedLoans(borrower);
    }

    @GetMapping(value = "/loan/lent")
    public List<Loan> findLentLoans(@RequestHeader String authorization) {
        User lender = tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllLentLoans(lender);
    }
}
