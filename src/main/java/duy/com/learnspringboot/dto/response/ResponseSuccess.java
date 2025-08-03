package duy.com.learnspringboot.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseSuccess extends ResponseEntity<ResponseSuccess.Payload> {

    // PUT, PATCH, DELETE
    public ResponseSuccess(HttpStatusCode status, String message) {
        super(new Payload(status.value(), message), HttpStatus.OK);
    }

    // POST, GET
    public ResponseSuccess(HttpStatusCode status, String message, Object data) {
        super(new Payload(message, status.value(), data), HttpStatus.OK);
    }

    public static class Payload {
        private final String message;
        private final int status;
        private Object data;

        public Payload(String message, int status, Object data) {
            this.message = message;
            this.status = status;
            this.data = data;
        }

        public Payload(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }

        public Object getData() {
            return data;
        }
    }
}
