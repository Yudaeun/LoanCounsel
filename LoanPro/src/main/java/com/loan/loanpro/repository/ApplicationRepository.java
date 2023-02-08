package com.loan.loanpro.repository;

import com.loan.loanpro.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
}
