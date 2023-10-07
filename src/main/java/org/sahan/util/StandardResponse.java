package org.sahan.util;

import lombok.Getter;

@Getter
public class StandardResponse {
    private int code;
    private String message;
    private Object data;

    public StandardResponse() {
    }

    public StandardResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
