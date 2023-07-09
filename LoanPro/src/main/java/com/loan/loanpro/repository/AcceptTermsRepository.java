package com.loan.loanpro.repository;

import com.loan.loanpro.domain.AcceptTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcceptTermsRepository extends JpaRepository<AcceptTerms,Long> {
}
