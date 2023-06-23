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
@DynamicUpdate
@DynamicInsert
@Where(clause="is_deleted=false")
public class Terms extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable=false,updatable=false)
    private Long termsId;

    @Column(columnDefinition="varchar(255) NOT NULL comment '약관'")
    private String content;

    @Column(columnDefinition="varchar(255) NOT NULL comment '약관상세 URL'")
    private String termsDetailUrl;


}
