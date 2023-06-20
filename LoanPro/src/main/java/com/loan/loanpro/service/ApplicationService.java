package com.loan.loanpro.service;

import com.loan.loanpro.dto.ApplicationDTO;

public interface ApplicationService {
    ApplicationDTO.Response create(ApplicationDTO.Request request);
    ApplicationDTO.Response get(Long applicationId);
    ApplicationDTO.Response update(Long applicationId, ApplicationDTO.Request request);
    void delete(Long applicationId);
}
