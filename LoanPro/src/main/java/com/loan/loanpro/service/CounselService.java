package com.loan.loanpro.service;

import com.loan.loanpro.dto.CounselDTO.Request;
import com.loan.loanpro.dto.CounselDTO.Response;


public interface CounselService {
    Response create(Request request);
    Response get(Long counselId);
    Response update(Long counselId, Request request);
    void delete(Long counselId);
}
