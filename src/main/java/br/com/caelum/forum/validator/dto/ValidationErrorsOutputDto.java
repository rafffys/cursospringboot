package br.com.caelum.forum.validator.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsOutputDto {
    private List<String> globalErrorMessages = new ArrayList<>();
    private List<FieldErrorOutputDto> fieldErrors = new ArrayList();

    public void addError(String message) {
        globalErrorMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        FieldErrorOutputDto fieldErrorOutputDto = new FieldErrorOutputDto(field, message);
        fieldErrors.add(fieldErrorOutputDto);
    }

    public List<String> getGlobalErrorMessages() {
        return this.globalErrorMessages;
    }

    public List<FieldErrorOutputDto> getErrors() {
        return this.fieldErrors;
    }

    public int getNumberOfErrors() {
        return this.globalErrorMessages.size() + this.fieldErrors.size();
    }
}
