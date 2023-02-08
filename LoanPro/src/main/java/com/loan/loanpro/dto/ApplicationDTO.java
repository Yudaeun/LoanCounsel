package com.loan.loanpro.dto;

import lombok.*;
import net.bytebuddy.asm.Advice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ApplicationDTO implements Serializable {
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request{
        private String name;
        private String cellphone;
        private String email;
        private BigDecimal homeAmount;
    }
    public static class Response{
        private String name;
        private String cellphone;
        private String email;
        private BigDecimal homeAmount;
        private Long appId;
        private LocalDateTime appliedAt;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;

    }
}
