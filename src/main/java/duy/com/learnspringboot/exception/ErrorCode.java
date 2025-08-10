package duy.com.learnspringboot.exception;

public enum ErrorCode {
    VALIDATION_ERROR(1, "Validation failed"),

    UNAUTHORIZED(401, "Unauthorized"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // user error code, from 1001 to 1999
    USER_NOT_FOUND(1001, "User not found");


    private int code;
    private String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
