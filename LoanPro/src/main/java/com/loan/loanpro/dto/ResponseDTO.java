package com.loan.loanpro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.loan.loanpro.exception.BaseException;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> implements Serializable {

    private ResultObject result;

    private T data;

    public ResponseDTO(ResultObject result) {
        this.result = result;
    }

    public ResponseDTO(T data) {
        this.data = data;
    }

    public static <T> ResponseDTO<T> ok() {
        return new ResponseDTO<>(ResultObject.getSuccess());
    }
    //오버로딩->다형성 보장
    public static <T> ResponseDTO<T> ok(T data) {
        return new ResponseDTO<>(ResultObject.getSuccess(), data);
    }

    public static <T> ResponseDTO<T> response(T data) {
        return new ResponseDTO<>(ResultObject.getSuccess(), data);
    }

    public ResponseDTO(BaseException ex) {
        this.result = new ResultObject(ex);
    }
}
