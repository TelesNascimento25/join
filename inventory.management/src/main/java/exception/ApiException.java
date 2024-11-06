package exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import model.dto.ErrorCode;

@AllArgsConstructor
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = -4566733207577274173L;
    private final int statusCode;

    public ApiException(ErrorCode errorCode, Object... params) {
        super(errorCode.getMessage(params));  
        this.statusCode = errorCode.getStatus().value();  
    }

    public HttpStatus getStatus() {
        return HttpStatus.valueOf(statusCode);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
