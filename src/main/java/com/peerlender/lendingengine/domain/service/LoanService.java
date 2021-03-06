package com.peerlender.lendingengine.domain.service;

import com.peerlender.lendingengine.domain.exception.LoanApplicationNotFoundException;
import com.peerlender.lendingengine.domain.exception.LoanNotFoundException;
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

    @Transactional // Used in case of unexpected behaviour which may result to duplicated or partially completed
    // actions, with @Transactional this situation is avoided
    public void acceptLoan(final String lenderUsername, final long loanApplicationId) {
        User lender = getLender(lenderUsername);
        LoanApplication loanApplication = getLoanApplication(loanApplicationId);
        loanRepository.save(loanApplication.acceptLoanApplication(lender));
    }

    @Transactional
    public void repayLoan(final Money money,final int id, final User borrower) {
        Loan loan = loanRepository.findByIdAndBorrower(id,borrower).orElseThrow(() -> new LoanNotFoundException(id));
        Money paidAmount = (money.getAmount() > loan.getAmountOwed().getAmount()) ?
                loan.getAmountOwed() : money;
        loan.repay(paidAmount);
    }

    public List<Loan> findAllBorrowedLoans(final User borrower, final Status status) {
        return loanRepository.findAllByBorrowerAndStatus(borrower,status);
    }

    public List<Loan> findAllLentLoans(final User lender, final Status status) {
        return loanRepository.findAllByLenderAndStatus(lender, status);
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
