package com.peerlender.lendingengine.domain.service;

import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanApplicationAdapter {

    private final UserRepository userRepository;

    @Autowired
    public LoanApplicationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method transforms LoanRequest objects to a LoanApplication object to be used in LoanController class
     * @param loanRequest the LoanRequest to be transformed
     * @return the LoanApplication object
     */
    public LoanApplication transform(LoanRequest loanRequest, User borrower) {
        /*Optional is primarily intended for use as a method return type where there is a clear need to represent
        "no result," and where using null is likely to cause errors. A variable whose type is Optional should never
        itself be null; it should always point to an Optional instance.*/
        Optional<User> userOptional = userRepository.findById(borrower.getUsername());

        if (userOptional.isPresent()) {
            return new LoanApplication(loanRequest.getAmount(),
                    userOptional.get(),
                    loanRequest.getRepaymentTerm(),
                    loanRequest.getInterestRate());
        }else {
            throw new UserNotFoundException(borrower.getUsername());
        }
    }
}
