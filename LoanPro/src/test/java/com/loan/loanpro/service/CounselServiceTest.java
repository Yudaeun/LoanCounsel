package com.loan.loanpro.service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import com.loan.loanpro.domain.Counsel;
import com.loan.loanpro.dto.CounselDTO.Request;
import com.loan.loanpro.dto.CounselDTO.Response;
import com.loan.loanpro.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
}
