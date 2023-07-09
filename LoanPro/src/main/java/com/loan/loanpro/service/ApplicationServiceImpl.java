package com.loan.loanpro.service;

import com.loan.loanpro.domain.AcceptTerms;
import com.loan.loanpro.domain.Application;
import com.loan.loanpro.domain.Terms;
import com.loan.loanpro.dto.ApplicationDTO;
import com.loan.loanpro.exception.BaseException;
import com.loan.loanpro.exception.ResultType;
import com.loan.loanpro.repository.AcceptTermsRepository;
import com.loan.loanpro.repository.ApplicationRepository;
import com.loan.loanpro.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;
    private final TermsRepository termsRepository;
    private final AcceptTermsRepository acceptTermsRepository;

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

    @Override
    public Boolean acceptTerms(Long applicationId, ApplicationDTO.AcceptTerms request) {
        applicationRepository.findById(applicationId).orElseThrow(()->{
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        List<Terms> termsList=termsRepository.findAll(Sort.by(Sort.Direction.ASC,"termsId"));
        if(termsList.isEmpty()){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        } //약관은 하나이상 존재해야 한다.

        List<Long> acceptTermsIds=request.getAcceptTermsIds();
        if(termsList.size()!=acceptTermsIds.size()){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }//고객이 신청한 약관의 수=존재하는 약관의 수

        List<Long> termsIds=termsList.stream().map(Terms::getTermsId).collect(Collectors.toList());
        Collections.sort(acceptTermsIds);

        if(!termsIds.containsAll(acceptTermsIds)){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        } //가지고 있는 약관과 다른 결과가 있을 때

        for(Long termsId:acceptTermsIds){
            AcceptTerms acceptTerms=AcceptTerms.builder()
                    .termsId(termsId)
                    .applicationId(applicationId)
                    .build();

            acceptTermsRepository.save(acceptTerms);
        }

        return true;
    }


}
