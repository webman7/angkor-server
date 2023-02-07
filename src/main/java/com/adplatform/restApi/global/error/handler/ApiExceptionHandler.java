package com.adplatform.restApi.global.error.handler;

import com.adplatform.restApi.user.exception.UserAlreadyExistException;
import com.adplatform.restApi.user.exception.UserException;
import com.adplatform.restApi.user.exception.UserLoginFailedException;
import com.adplatform.restApi.global.config.security.exception.SecurityException;
import com.adplatform.restApi.global.error.dto.ErrorResponse;
import com.adplatform.restApi.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletRequest;
import java.text.MessageFormat;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {
    private static final String UNKNOWN_EXCEPTION_CODE = "unknownException.code";
    private static final String UNKNOWN_EXCEPTION_MESSAGE = "unknownException.message";
    private static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION_CODE = "methodArgumentNotValidException.code";
    private static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE = "methodArgumentNotValidException.message";

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResponse handleDefaultException(Exception e, ServletRequest request) {
        log.error(e.toString());
        return this.createErrorResponse(request, UNKNOWN_EXCEPTION_CODE, UNKNOWN_EXCEPTION_MESSAGE);
    }

    @SuppressWarnings("ConstantConditions")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ErrorResponse handleValidationException(
            BindException e, ServletRequest request) {
        return this.createErrorResponse(
                request,
                e.getBindingResult().getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                DefaultMessageSourceResolvable::getDefaultMessage,
                                (m1, m2) -> String.format("%s, %s", m1, m2))));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getLocalizedMessage();
        String enumValues = message.substring(message.indexOf("["), message.indexOf("]") + 1);
        String errorMessage = MessageFormat.format("The value must correspond to: {0}", enumValues);
        return ErrorResponse.create(1000, errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ErrorResponse.create(1000, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SecurityException.class)
    protected ErrorResponse handleSecurityException(SecurityException e, ServletRequest request) {
        return this.createErrorResponse(request, e.getCodeKey(), e.getMessageKey());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    protected ErrorResponse handleBaseException(BaseException e, ServletRequest request) {
        return this.createErrorResponse(request, e.getCodeKey(), e.getMessageKey());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({UserLoginFailedException.class, UserAlreadyExistException.class})
    protected ErrorResponse handleUserConflictException(UserException e, ServletRequest request) {
        return this.createErrorResponse(request, e.getCodeKey(), e.getMessageKey());
    }

    private ErrorResponse createErrorResponse(ServletRequest request, String codeKey, String messageKey) {
        return ErrorResponse.create(
                Integer.parseInt(this.getMessage(codeKey, request)),
                this.getMessage(messageKey, request));
    }

    private ErrorResponse createErrorResponse(ServletRequest request, Map<String, String> errors) {
        return ErrorResponse.create(
                Integer.parseInt(this.getMessage(METHOD_ARGUMENT_NOT_VALID_EXCEPTION_CODE, request)),
                this.getMessage(METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE, request),
                errors);
    }

    private String getMessage(String key, ServletRequest request) {
        return this.messageSource.getMessage(key, null, request.getLocale());
    }
}