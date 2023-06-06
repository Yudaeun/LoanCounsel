package com.loan.loanpro.service;

import com.loan.loanpro.domain.Application;
import com.loan.loanpro.dto.ApplicationDTO;
import com.loan.loanpro.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApplicationDTO.Response create(ApplicationDTO.Request request) {
        Application application=modelMapper.map(request,Application.class);
        application.setAppliedTime(LocalDateTime.now());

        Application applied=applicationRepository.save(application); //요청값 받아와서 저장
        return modelMapper.map(applied, ApplicationDTO.Response.class); //응답 객체로 매핑해서 반환
    }
}
