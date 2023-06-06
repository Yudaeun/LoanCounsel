package com.loan.loanpro.service;

import com.loan.loanpro.domain.Application;
import com.loan.loanpro.dto.ApplicationDTO;
import com.loan.loanpro.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void 애플리케이션요청이들어오면_애플리케이션응답엔티티제대로생기는지(){
        Application entity=Application.builder()
                .name("Yuyuyu")
                .cellphone("010-1234-5555")
                .email("udu5771@naver.com")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        ApplicationDTO.Request request= ApplicationDTO.Request.builder()
                .name("Yuyuyu")
                .cellphone("010-1234-5555")
                .email("udu5771@naver.com")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
        ApplicationDTO.Response actual=applicationService.create(request);

        assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
    }
}
