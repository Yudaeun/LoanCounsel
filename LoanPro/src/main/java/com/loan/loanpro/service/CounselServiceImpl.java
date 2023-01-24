package com.loan.loanpro.service;

import com.loan.loanpro.domain.Counsel;
import com.loan.loanpro.dto.CounselDTO.Response;
import com.loan.loanpro.dto.CounselDTO.Request;
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
        System.out.println(request.getCellPhone());
        Counsel counsel=modelMapper.map(request, Counsel.class); //요청값이 Counsel 엔티티로 매핑
//        System.out.println(counsel.getAddress());
        counsel.builder().
                appliedDate(LocalDateTime.now()).
                build();
//    counsel.setAppliedDate(LocalDateTime.now());
//        System.out.println(counsel.getAddress()+counsel.getCellPhone());
        Counsel created=counselRepository.save(counsel);
//        System.out.println(created.getAddress()+created.getEmail());
        return modelMapper.map(created,Response.class);
    }
}
