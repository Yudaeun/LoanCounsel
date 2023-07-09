package com.loan.loanpro.controller;

import com.loan.loanpro.dto.ApplicationDTO;
import com.loan.loanpro.dto.ResponseDTO;
import com.loan.loanpro.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import static com.loan.loanpro.dto.ResponseDTO.ok;

@RequiredArgsConstructor
@RestController
@Description("대출 신청 API")
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseDTO<ApplicationDTO.Response> create(@RequestBody ApplicationDTO.Request request){
        return ok(applicationService.create(request));
    }

    @GetMapping("/{applicationId}")
    public ResponseDTO<ApplicationDTO.Response> get(@PathVariable Long applicationId){
        return ok(applicationService.get(applicationId));
    }

    @PutMapping("/{applicationId}")
    public ResponseDTO<ApplicationDTO.Response> update(@PathVariable Long applicationId,@RequestBody ApplicationDTO.Request request){
        return ok(applicationService.update(applicationId,request));
    }
    @DeleteMapping("/{applicationId}")
    public ResponseDTO<Void> delete(@PathVariable Long applicationId){
        applicationService.delete(applicationId);
        return ok();
    }

    @PostMapping("/{applicationId}/terms")
    public ResponseDTO<Boolean> acceptTerms(@PathVariable Long applicationId,@RequestBody ApplicationDTO.AcceptTerms request){
        return ok(applicationService.acceptTerms(applicationId,request));
    }
}
