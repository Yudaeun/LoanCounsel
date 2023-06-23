package com.loan.loanpro.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TermsDTO implements Serializable {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request{
        private String content;
        private String termsDetailUrl;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Response{
        private String termsId;
        private String content;
        private String termsDetailURL;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
    }

}
