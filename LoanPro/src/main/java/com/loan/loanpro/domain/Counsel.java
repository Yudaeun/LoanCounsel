package com.loan.loanpro.domain;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause="is_deleted=false") //soft delete를 사용해서 삭제 여부를 제어
public class Counsel extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false,updatable=false)
    private Long counselId;

    @Column(columnDefinition = "datetime COMMENT '신청일자'")
    private LocalDateTime appliedDate;


    @Column(columnDefinition = "varchar(12) COMMENT '상담요청자'")
    private String name;


    @Column(columnDefinition = "varchar(23) COMMENT '전화번호'")
    private String cellPhone;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT '상담자 이메일'")
    private String email;

    @Column(columnDefinition = "text DEFAULT NULL COMMENT '상담메모'")
    private String memo;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT '주소'")
    private String address;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT '상세주소'")
    private String addressDetail;

    @Column(columnDefinition = "varchar(5) DEFAULT NULL COMMENT '우편번호'")
    private String zipCode;

    @Column(columnDefinition = "int DEFAULT 0 COMMENT '연봉'")
    private Integer salary;

    @Column(columnDefinition = "int DEFAULT 0 COMMENT '나이'")
    private Integer age;

    @Column(columnDefinition = "bit DEFAULT false COMMENT '직장여부'")
    private Boolean isWork;
}
