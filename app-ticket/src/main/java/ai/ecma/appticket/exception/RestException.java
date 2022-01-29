package ai.ecma.appticket.exception;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException{

    private HttpStatus status;
    private String message;
    private Object object;

    public RestException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public RestException(HttpStatus status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }
}
