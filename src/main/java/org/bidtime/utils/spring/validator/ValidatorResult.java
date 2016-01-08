package org.bidtime.utils.spring.validator;

/**
 * Created by libing on 2015/12/22.
 * idbutils
 */

public class ValidatorResult {
    //通过true;
    private Boolean result;
    private String msg;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean isPass() {
        return this.result == null ? true : this.result;
    }
}
