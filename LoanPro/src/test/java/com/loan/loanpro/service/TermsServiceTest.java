package com.loan.loanpro.service;

import com.loan.loanpro.domain.Terms;
import com.loan.loanpro.dto.TermsDTO;
import com.loan.loanpro.repository.TermsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TermsServiceTest {

    @InjectMocks
    TermsServiceImpl termsService;

    @Mock
    private TermsRepository termsRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void 약관엔티티가_생성되었는가(){
        Terms entity= Terms.builder()
                .content("대출 이용 약관")
                .termsDetailUrl("https://naver.com")
                        .build();

        TermsDTO.Request request=TermsDTO.Request.builder()
                .content("대출 이용 약관")
                .termsDetailUrl("https://naver.com")
                .build();

        when(termsRepository.save(ArgumentMatchers.any(Terms.class))).thenReturn(entity);

        TermsDTO.Response actual=termsService.create(request);

        assertThat(actual.getContent()).isSameAs(entity.getContent());
        assertThat(actual.getTermsDetailURL()).isSameAs(entity.getTermsDetailUrl());

    }

    @Test
    void 모든_약관동의_엔티티가_반환되는가(){
        Terms entity1= Terms.builder()
                .content("대출 이용약관 1")
                .termsDetailUrl("https://naver.com")
                .build();
        Terms entity2=Terms.builder()
                .content("대출 이용약관 2")
                .termsDetailUrl("https://naver.com")
                .build();

        List<Terms> list=new ArrayList<>(Arrays.asList(entity1,entity2));

        when(termsRepository.findAll()).thenReturn(list);

        List<TermsDTO.Response> actual=termsService.getAll();

        assertThat(actual.size()).isSameAs(list.size());
    }
}
