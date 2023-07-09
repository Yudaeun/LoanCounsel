package com.loan.loanpro.dto;

import lombok.*;
import net.bytebuddy.asm.Advice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ApplicationDTO implements Serializable {
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request{
        private String name;
        private String cellPhone;
        private String email;
        private BigDecimal hopeAmount;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response{
        private String name;
        private String cellPhone;
        private String email;
        private BigDecimal hopeAmount;
        private Long appId;
        private LocalDateTime appliedTime;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class AcceptTerms{
        List<Long> acceptTermsIds;
    }
}
