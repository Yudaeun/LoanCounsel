package com.loan.loanpro.controller;

import com.loan.loanpro.dto.ResponseDTO;
import com.loan.loanpro.dto.TermsDTO;
import com.loan.loanpro.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.loan.loanpro.dto.ResponseDTO.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terms")
public class TermsController {
    private final TermsService termsService;

    @PostMapping
    public ResponseDTO<TermsDTO.Response> create(@RequestBody TermsDTO.Request request){
        return ok(termsService.create(request));
    }

    @GetMapping
    public ResponseDTO<List<TermsDTO.Response>> getAll(){
        return ok(termsService.getAll());
    }
}
