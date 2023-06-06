package com.loan.loanpro.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CounselDTO implements Serializable {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request{

        private String name;
        private String cellPhone;
        private String email;
        private String memo;
        private String address;
        private String addressDetail;
        private String zipCode;
        private Integer age;
        private Integer salary;
        private Boolean isWork;
        private Boolean isDeleted;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response{

        private Long counselId;
        private String name;
        private String cellPhone;
        private String email;
        private String memo;
        private String address;
        private String addressDetail;
        private String zipCode;
        private Integer age;
        private Integer salary;
        private Boolean isWork;
        private Boolean isDeleted;
        private LocalDateTime appliedDate;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
    }

}
