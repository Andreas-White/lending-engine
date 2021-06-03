package com.peerlender.lendingengine.domain.repository;

import com.peerlender.lendingengine.domain.model.Loan;
import com.peerlender.lendingengine.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findAllByBorrower(User borrower);
    List<Loan> findAllByLender(User lender);
    Optional<Loan> findByIdAndBorrower(int id, User borrower);
}
