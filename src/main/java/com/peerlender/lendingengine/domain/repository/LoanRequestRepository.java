package com.peerlender.lendingengine.domain.repository;


import com.peerlender.lendingengine.domain.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRequestRepository extends JpaRepository<LoanApplication, Long> {

}
