package com.loan.loanpro.repository;

import com.loan.loanpro.domain.Terms;
import com.loan.loanpro.service.TermsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepository extends JpaRepository<Terms,Long> {

}
