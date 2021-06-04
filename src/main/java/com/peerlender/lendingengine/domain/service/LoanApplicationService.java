package com.peerlender.lendingengine.domain.service;

import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.Status;
import com.peerlender.lendingengine.domain.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    @Autowired
    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    public void applyForLoan(LoanApplication loanApplication) {
        loanApplicationRepository.save(loanApplication);
    }

    public List<LoanApplication> getAllLoanApplications(Status status) {
        return loanApplicationRepository.findAllByStatusEquals(status);
    }


}
