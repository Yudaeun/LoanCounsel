package com.loan.loanpro.service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import com.loan.loanpro.domain.Counsel;
import com.loan.loanpro.dto.CounselDTO.Request;
import com.loan.loanpro.dto.CounselDTO.Response;
import com.loan.loanpro.exception.BaseException;
import com.loan.loanpro.exception.ResultType;
import com.loan.loanpro.repository.CounselRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CounselServiceTest {
    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel(){
        Counsel entity= Counsel.builder()
                .name("Riri")
                .cellPhone("010-1234-5678")
                .email("udu5771@naver.com")
                .zipCode("06254")
                .address("서울시 강남구 역삼동 620-10")
                .addressDetail("305호")
                .isWork(false)
                .salary(1000)
                .age(20)
                .build();

        Request request=Request.builder()
                .name("Riri")
                .cellPhone("010-1234-5678")
                .email("udu5771@naver.com")
                .zipCode("06254")
                .address("서울시 강남구 역삼동 620-10")
                .addressDetail("305호")
                .isWork(false)
                .salary(1000)
                .age(20)
                .build();
        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        Response actual=counselService.create(request);

        System.out.println(entity.getCounselId());
        assertThat(actual.getName()).isSameAs(entity.getName());
       // assertThat(actual.getCounselId()).isNotNull();
    }

    @Test
    void 상담엔티티가_존재할때_상담아이디가있으면_true(){
        Long findId=1L;

        Counsel entity=Counsel.builder()
                .counselId(1L)
                .build();
        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual=counselService.get(findId);
        assertThat(actual.getCounselId()).isSameAs(findId);
    }

    @Test
    void get_없는_엔티티를_요청했을때_예외반환(){
        Long findId=2L;

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        Assertions.assertThrows(BaseException.class,()->counselService.get(findId));
    }
}
