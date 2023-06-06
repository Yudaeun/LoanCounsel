package com.loan.loanpro.service;

import com.loan.loanpro.domain.Counsel;
import com.loan.loanpro.dto.CounselDTO.Response;
import com.loan.loanpro.dto.CounselDTO.Request;
import com.loan.loanpro.exception.BaseException;
import com.loan.loanpro.exception.ResultType;
import com.loan.loanpro.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService{
    private final ModelMapper modelMapper;
    private final CounselRepository counselRepository;

    @Override
    public Response create(Request request){
       // System.out.println(request.getCellPhone());
        Counsel counsel=modelMapper.map(request,Counsel.class);//요청값이 Counsel 엔티티로 매핑
//        System.out.println(counsel.getAddress());
//        System.out.println(request.getCellPhone());
        counsel.setAppliedDate(LocalDateTime.now());
//        System.out.println(counsel.getAddress()+counsel.getCellPhone());
        Counsel created=counselRepository.save(counsel);
        System.out.println(created.getAddress()+created.getEmail());
        return modelMapper.map(created,Response.class);
    }
    @Override
    public Response get(Long counselId) {
        //상담 정보 get 요청을 했을 때 정보 있으면 모델매퍼에서 불러와서 정보 반환 없으면 예외
        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
    return modelMapper.map(counsel,Response.class);
    }

    @Override
    public Response update(Long counselId, Request request) {
        Counsel counsel=counselRepository.findById(counselId).orElseThrow(()->{
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        counsel=counsel.builder().
                counselId(counsel.getCounselId()).
                appliedDate(counsel.getAppliedDate()).
                name(request.getName()).
                cellPhone(request.getCellPhone()).
                email(request.getEmail()).
                salary(request.getSalary()).
                memo(request.getMemo()).
                age(request.getAge()).
                address(request.getAddress()).
                addressDetail(request.getAddressDetail()).
                zipCode(request.getZipCode()).
                build();

//        System.out.println(counsel.getName()+request.getName()+counsel.getCounselId());
        counselRepository.save(counsel);
        return modelMapper.map(counsel,Response.class);
    }

    @Override
    public void delete(Long counselId) {
        Counsel counsel=counselRepository.findById(counselId).orElseThrow(()->{
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        counsel.setIsDeleted(true);
        counselRepository.save(counsel);
    }
}
