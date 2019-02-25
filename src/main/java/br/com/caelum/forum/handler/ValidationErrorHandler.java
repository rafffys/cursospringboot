package br.com.caelum.forum.handler;

import br.com.caelum.forum.validator.dto.ValidationErrorsOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorsOutputDto handleValidationError(MethodArgumentNotValidException e) {
        List<ObjectError> globalErrors = e.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        ValidationErrorsOutputDto validationErrorsOutputDto = new ValidationErrorsOutputDto();
        globalErrors.forEach(objectError -> validationErrorsOutputDto.addError(getErrorMessage(objectError)));

        fieldErrors.forEach(fieldError -> {
           String errorMessage = getErrorMessage(fieldError);
           validationErrorsOutputDto.addFieldError(fieldError.getField(), errorMessage);
        });

        return validationErrorsOutputDto;
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
