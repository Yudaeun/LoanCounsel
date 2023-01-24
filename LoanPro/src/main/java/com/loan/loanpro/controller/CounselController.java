package com.loan.loanpro.controller;

import com.loan.loanpro.domain.Counsel;
import com.loan.loanpro.dto.CounselDTO.Request;
import com.loan.loanpro.dto.CounselDTO.Response;
import com.loan.loanpro.dto.ResponseDTO;
import com.loan.loanpro.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/counsels")
public class CounselController extends AbstController {
    private final CounselService counselService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request){

        return ok(counselService.create(request));
    }
}
