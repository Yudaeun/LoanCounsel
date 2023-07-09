package com.loan.loanpro.service;

import com.loan.loanpro.domain.AcceptTerms;
import com.loan.loanpro.domain.Application;
import com.loan.loanpro.domain.BaseEntity;
import com.loan.loanpro.domain.Terms;
import com.loan.loanpro.dto.ApplicationDTO;
import com.loan.loanpro.exception.BaseException;
import com.loan.loanpro.repository.AcceptTermsRepository;
import com.loan.loanpro.repository.ApplicationRepository;
import com.loan.loanpro.repository.TermsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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

    @Mock
    private TermsRepository termsRepository;

    @Mock
    private AcceptTermsRepository acceptTermsRepository;

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

    @Test
    void 약관동의를_하면_제대로_되는가(){
        Terms entityA=Terms.builder()
                .termsId(1L)
                .content("약관 1")
                .termsDetailUrl("https://naver.com")
                .build();

        Terms entityB=Terms.builder()
                .termsId(2L)
                .content("약관 2")
                .termsDetailUrl("https://naver.com")
                .build();

        List<Long> acceptTerms= Arrays.asList(1L,2L);

        ApplicationDTO.AcceptTerms request=ApplicationDTO.AcceptTerms.builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId=1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build())
        );

        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC,"termsId")))
                .thenReturn(Arrays.asList(entityA,entityB));

        when(acceptTermsRepository.save(ArgumentMatchers.any(AcceptTerms.class)))
                .thenReturn(AcceptTerms.builder().build());

        Boolean actual=applicationService.acceptTerms(findId,request);

        assertThat(actual).isTrue();
    }

    @Test
    void 약관동의를_제대로_안하면_예외가_발생하는가(){
        Terms entityA=Terms.builder()
                .termsId(1L)
                .content("약관 1")
                .termsDetailUrl("https://naver.com")
                .build();

        Terms entityB=Terms.builder()
                .termsId(2L)
                .content("약관 2")
                .termsDetailUrl("https://naver.com")
                .build();

        List<Long> acceptTerms= Arrays.asList(1L);

        ApplicationDTO.AcceptTerms request=ApplicationDTO.AcceptTerms.builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId=1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build())
        );

        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC,"termsId")))
                .thenReturn(Arrays.asList(entityA,entityB));

        Assertions.assertThrows(BaseException.class,()->applicationService.acceptTerms(findId,request));
    }
    @Test
    void 존재하지않는_약관에_동의하면_예외가_발생하는가(){
        Terms entityA=Terms.builder()
                .termsId(1L)
                .content("약관 1")
                .termsDetailUrl("https://naver.com")
                .build();

        Terms entityB=Terms.builder()
                .termsId(2L)
                .content("약관 2")
                .termsDetailUrl("https://naver.com")
                .build();

        List<Long> acceptTerms= Arrays.asList(1L,3L);

        ApplicationDTO.AcceptTerms request=ApplicationDTO.AcceptTerms.builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId=1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build())
        );

        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC,"termsId")))
                .thenReturn(Arrays.asList(entityA,entityB));

        Assertions.assertThrows(BaseException.class,()->applicationService.acceptTerms(findId,request));
    }
}
