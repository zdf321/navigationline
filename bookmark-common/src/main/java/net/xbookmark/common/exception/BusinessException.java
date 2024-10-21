package net.xbookmark.common.exception;

import net.xbookmark.common.response.StatusCode;

public class BusinessException extends RuntimeException {

    private String code;
    private String message;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.code = StatusCode.FAILED;
        this.message = message;
    }

    @Override
    public String getMessage() {
        String superMessage = super.getMessage();
        return null == superMessage ? message : superMessage;
    }

    public String getCode() {
        return this.code;
    }
}
