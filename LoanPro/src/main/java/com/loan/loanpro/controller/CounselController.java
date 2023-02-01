package com.loan.loanpro.controller;

import com.loan.loanpro.domain.Counsel;
import com.loan.loanpro.dto.CounselDTO.Request;
import com.loan.loanpro.dto.CounselDTO.Response;
import com.loan.loanpro.dto.ResponseDTO;
import com.loan.loanpro.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/counsels")
public class CounselController extends AbstController {
    private final CounselService counselService;

    @Description("대출 상담 정보 입력")
    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request){

        return ok(counselService.create(request));
    }

    @Description("대출 상담 정보 조회")
    @GetMapping("/{counselId}")
    public ResponseDTO<Response> get(@PathVariable Long counselId){
        return ok(counselService.get(counselId));
    }
}
