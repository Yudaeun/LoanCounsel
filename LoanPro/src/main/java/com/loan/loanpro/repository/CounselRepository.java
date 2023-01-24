package com.loan.loanpro.repository;

import com.loan.loanpro.domain.Counsel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounselRepository extends JpaRepository<Counsel,Long> {

}
