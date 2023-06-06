package com.loan.loanpro.controller;

import com.loan.loanpro.dto.ApplicationDTO;
import com.loan.loanpro.dto.ResponseDTO;
import com.loan.loanpro.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.loan.loanpro.dto.ResponseDTO.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseDTO<ApplicationDTO.Response> create(@RequestBody ApplicationDTO.Request request){
        return ok(applicationService.create(request));
    }
}
