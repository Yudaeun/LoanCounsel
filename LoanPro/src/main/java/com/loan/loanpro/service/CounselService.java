package com.loan.loanpro.service;

import com.loan.loanpro.dto.CounselDTO.Request;
import com.loan.loanpro.dto.CounselDTO.Response;


public interface CounselService {
    Response create(Request request);
}
