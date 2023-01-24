package com.loan.loanpro.controller;

import com.loan.loanpro.dto.ResponseDTO;
import com.loan.loanpro.dto.ResultObject;

public abstract class AbstController { //요청에 대한 응답값을 통일하기 위함
    protected <T> ResponseDTO<T> ok() {
        return ok(null, ResultObject.getSuccess());
    }

    protected <T> ResponseDTO<T> ok(T data) {
        return ok(data, ResultObject.getSuccess());
    }

    protected <T> ResponseDTO<T> ok(T data, ResultObject result) {
        ResponseDTO<T> obj = new ResponseDTO<>();
        obj.setResult(result);
        obj.setData(data);

        return obj;
    }

}
