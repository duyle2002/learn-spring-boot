package duy.com.learnspringboot.exception;

public enum ErrorCode {
    VALIDATION_ERROR(1, "Validation failed"),

    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    // user error code, from 1001 to 1999
    USER_NOT_FOUND(1001, "User not found"),
    USER_ALREADY_EXISTS(1002, "User already exists"),

    // permission error code, from 2001 to 2999
    PERMISSION_ALREADY_EXISTS(2001, "Permission already exists"),
    PERMISSION_NOT_FOUND(2002, "Permission not found"),

    // role error code, from 3001 to 3999
    ROLE_ALREADY_EXISTS(3001, "Role already exists"),
    ROLE_NOT_FOUND(3002, "Role not found");


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
