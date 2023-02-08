package com.loan.loanpro.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

//기본적으로 정의되어 있어야 하는 컬럼들

@JsonInclude //원하는 값만 json으로 변환
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass //객체의 입장에서 공통 속성 상속
@EntityListeners(AuditingEntityListener.class) //중복컬럼에 시간 데이터 매핑
public class BaseEntity implements Serializable {
    @CreatedDate
    @Column(updatable=false,columnDefinition = "datetime default CURRENT_TIMESTAMP NOT NULL COMMENT '생성일자")
    private LocalDateTime createdDate; //읽기전용, 생성일자는 바뀌면 안됨

    @LastModifiedDate
    @Column(columnDefinition = "datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '수정일자")
    private LocalDateTime updatedDate;

    @Column(columnDefinition = "bit default false NOT NULL COMMENT '이용가능여부")
    private Boolean isDeleted;
}
