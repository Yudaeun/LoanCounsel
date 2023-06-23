package com.loan.loanpro.service;

import com.loan.loanpro.domain.Terms;
import com.loan.loanpro.dto.ResponseDTO;
import com.loan.loanpro.dto.TermsDTO.Request;
import com.loan.loanpro.dto.TermsDTO.Response;

import java.util.List;


public interface TermsService {
   Response create(Request request);

   List<Response> getAll();

}
