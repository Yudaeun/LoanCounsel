package com.loan.loanpro.service;

import com.loan.loanpro.domain.Application;
import com.loan.loanpro.dto.ApplicationDTO;
import com.loan.loanpro.exception.BaseException;
import com.loan.loanpro.exception.ResultType;
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

    @Override
    public ApplicationDTO.Response get(Long applicationId) {
        Application application=applicationRepository.findById(applicationId).orElseThrow(()->{
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        return modelMapper.map(application, ApplicationDTO.Response.class);
    }

    @Override
    public ApplicationDTO.Response update(Long applicationId, ApplicationDTO.Request request) {
        Application application=applicationRepository.findById(applicationId).orElseThrow(()->{
                throw new BaseException(ResultType.SYSTEM_ERROR);}
        );

        application.setName(request.getName());
        application.setEmail(request.getEmail());
        application.setCellPhone(request.getCellPhone());
        application.setHopeAmount(request.getHopeAmount());
        System.out.println(request.getHopeAmount());
        System.out.println(application.getHopeAmount());

        applicationRepository.save(application);

        return modelMapper.map(application, ApplicationDTO.Response.class);
    }

    @Override
    public void delete(Long applicationId) {
        Application application=applicationRepository.findById(applicationId).orElseThrow(()->{
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setIsDeleted(true);

        applicationRepository.save(application);
    }

}
