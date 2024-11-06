package model.dto;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    PRODUCT_NOT_FOUND("Product with ID %d not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("Category with ID %d not found", HttpStatus.NOT_FOUND);

    private final String messageTemplate;
    private final HttpStatus status; 

    ErrorCode(String messageTemplate, HttpStatus status) {
        this.messageTemplate = messageTemplate;
        this.status = status;
    }

    public String getMessage(Object... params) {
        return String.format(messageTemplate, params);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
