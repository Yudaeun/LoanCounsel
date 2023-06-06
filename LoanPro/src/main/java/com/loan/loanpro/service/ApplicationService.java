package com.loan.loanpro.service;

import com.loan.loanpro.dto.ApplicationDTO;

public interface ApplicationService {
    ApplicationDTO.Response create(ApplicationDTO.Request request);

}
