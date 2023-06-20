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
import java.util.Optional;

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
                .cellPhone("010-1234-5555")
                .email("udu5771@naver.com")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        ApplicationDTO.Request request= ApplicationDTO.Request.builder()
                .name("Yuyuyu")
                .cellPhone("010-1234-5555")
                .email("udu5771@naver.com")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
        ApplicationDTO.Response actual=applicationService.create(request);

        assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
    }

    @Test
    void 애플리케이션엔티티가_존재하면_응답을_반환하는가(){
        Long findId=1L;

        Application entity=Application.builder()
                .appId(1L)
                .build();

        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        ApplicationDTO.Response actual=applicationService.get(findId);
        assertThat(actual.getAppId()).isSameAs(findId);
    }

    @Test
    void 업데이트하면_제대로_응답을_반환하는가(){
        Long findId=1L;

        Application entity=Application.builder()
                .appId(1L)
                .hopeAmount(BigDecimal.valueOf(30000000))
                .build();

        ApplicationDTO.Request request= ApplicationDTO.Request.builder()
                        .hopeAmount(BigDecimal.valueOf(50000000))
                                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        ApplicationDTO.Response actual=applicationService.update(findId,request);

        assertThat(actual.getAppId()).isSameAs(findId);
        assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
    }

    @Test
    void 삭제가_제대로_되는가(){
        Long findId=1L;

        Application entity=Application.builder()
                .appId(1L)
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        applicationService.delete(findId);
        assertThat(entity.getIsDeleted()).isSameAs(true);
    }
}
