package com.loan.loanpro.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause="is_deleted=false")
public class AcceptTerms extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false,updatable=false)
    private Long acceptTermsId;

    @Column(columnDefinition = "bigint NOT NULL COMMENT '신청ID'")
    private Long applicationId;

    @Column(columnDefinition = "bigint NOT NULL COMMENT '약관ID'")
    private Long termsId;
}
