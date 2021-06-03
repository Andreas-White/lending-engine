package com.peerlender.lendingengine.domain.service;

import com.peerlender.lendingengine.domain.exception.LoanApplicationNotFoundException;
import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.*;
import com.peerlender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerlender.lendingengine.domain.repository.LoanRepository;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LoanService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanApplicationRepository loanApplicationRepository,
                       UserRepository userRepository,
                       LoanRepository loanRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Transactional
    public void acceptLoan(final String lenderUsername, final long loanApplicationId) {
        User lender = getLender(lenderUsername);
        LoanApplication loanApplication = getLoanApplication(loanApplicationId);
        User borrower = loanApplication.getBorrower();
        Money money = new Money(Currency.USD,loanApplication.getAmount());
        lender.withdraw(money);
        borrower.topUp(money);
        loanRepository.save(new Loan(lender,loanApplication));
    }

    private LoanApplication getLoanApplication(long loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new LoanApplicationNotFoundException(loanApplicationId));
    }

    private User getLender(String lenderUsername) {
        return userRepository.findById(lenderUsername)
                .orElseThrow(() -> new UserNotFoundException(lenderUsername));
    }
}
