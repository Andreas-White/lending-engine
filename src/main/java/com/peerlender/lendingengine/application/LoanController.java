package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.application.model.LoanRepaymentRequest;
import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.application.service.TokenValidationService;
import com.peerlender.lendingengine.application.service.TokenValidationServiceImpl;
import com.peerlender.lendingengine.domain.model.Loan;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.Status;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.service.LoanApplicationAdapter;
import com.peerlender.lendingengine.domain.service.LoanApplicationService;
import com.peerlender.lendingengine.domain.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/loan")
public class LoanController {

    private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;
    private final TokenValidationService tokenValidationService;
    private final LoanApplicationService loanApplicationService;

    @Autowired
    public LoanController(LoanApplicationAdapter loanApplicationAdapter,
                          LoanService loanService,
                          TokenValidationService tokenValidationService,
                          LoanApplicationService loanApplicationService) {
        this.loanApplicationAdapter = loanApplicationAdapter;
        this.loanService = loanService;
        this.tokenValidationService = tokenValidationService;
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping(value = "/request")
    public void requestLoan(@RequestBody final LoanRequest loanRequest, @RequestHeader String authorization) {
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        loanApplicationService.applyForLoan(loanApplicationAdapter.transform(loanRequest,borrower));
    }

    @GetMapping(value = "/requests")
    public List<LoanApplication> findAllLoanApplications(@RequestHeader String authorization) {
        tokenValidationService.validateTokenAndGetUser(authorization);
        return loanApplicationService.getAllLoanApplications(Status.UNRESOLVED);
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

    @GetMapping(value = "/{status}/borrowed")
    public List<Loan> findBorrowedLoans(@RequestHeader String authorization,@PathVariable Status status) {
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllBorrowedLoans(borrower,status);
    }

    @GetMapping(value = "/{status}/lent")
    public List<Loan> findLentLoans(@RequestHeader String authorization,@PathVariable Status status) {
        User lender = tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllLentLoans(lender,status);
    }

    @PostMapping(value = "/repay")
    public void repayLoan(@RequestBody LoanRepaymentRequest loanRepaymentRequest,
                          @RequestHeader String authorization) {
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        loanService.repayLoan(loanRepaymentRequest.getAmount(),loanRepaymentRequest.getLoanId(),borrower);
    }
}
