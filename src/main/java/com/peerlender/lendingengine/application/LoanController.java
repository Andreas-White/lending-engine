package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.application.model.LoanRepaymentRequest;
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
@RequestMapping(value = "/loan")
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

    @PostMapping(value = "/request")
    public void requestLoan(@RequestBody final LoanRequest loanRequest, @RequestHeader String authorization) {
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        loanApplicationRepository.save(loanApplicationAdapter.transform(loanRequest, borrower));
    }

    @GetMapping(value = "/requests")
    public List<LoanApplication> findAllLoanApplications(@RequestHeader String authorization) {
        tokenValidationService.validateTokenAndGetUser(authorization);
        return loanApplicationRepository.findAll();
    }

    @PostMapping(value = "/accept/{loanApplicationId}")
    public void acceptLoan(@PathVariable String loanApplicationId, @RequestHeader String authorization) {
        User lender = tokenValidationService.validateTokenAndGetUser(authorization);
        loanService.acceptLoan(lender.getUsername(),Long.parseLong(loanApplicationId));
    }

    @GetMapping(value = "/all")
    public List<Loan> findAllLoans(@RequestHeader String authorization) {
        tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.getLoans();
    }

    @GetMapping(value = "/borrowed")
    public List<Loan> findBorrowedLoans(@RequestHeader String authorization) {
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllBorrowedLoans(borrower);
    }

    @GetMapping(value = "/lent")
    public List<Loan> findLentLoans(@RequestHeader String authorization) {
        User lender = tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllLentLoans(lender);
    }

    @PostMapping(value = "/repay")
    public void repayLoan(@RequestBody LoanRepaymentRequest loanRepaymentRequest,
                          @RequestHeader String authorization) {
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        loanService.repayLoan(loanRepaymentRequest.getAmount(),loanRepaymentRequest.getLoanId(),borrower);
    }
}
