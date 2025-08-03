package duy.com.learn_spring_boot.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseData<T> {
    private final int status;
    private final String message;

    // don't show null in the response
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResponseData(int status, String message, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public ResponseData(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
