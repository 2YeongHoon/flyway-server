package com.domain.core.exceptions.handler;

import com.domain.core.dto.ErrorResponse;
import com.domain.core.enums.Errors;
import com.domain.core.exceptions.BaseRuntimeException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 전역 예외 핸들러
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 인자 예외
     *
     * @param response HTTP 서블릿 응답객체
     * @param e        예외
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> BadRequestExceptionHandler(HttpServletResponse response,
        BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMessage.append(String.format("[%s: %s], ", fieldError.getField(), fieldError.getDefaultMessage()));
        });

        log.error("Validation error occurred: {}", errorMessage);
        return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, Errors.BAD_REQUEST, errorMessage.toString());
    }

    /**
     * 런타임 예외는 {@link Errors}에 정의된 예외 코드와 메시지로 처리한다.
     *
     * @param response HTTP 서블릿 응답객체
     * @param e        예외
     */
    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ErrorResponse> BaseRunTimeExceptionHandler(HttpServletResponse response,
        BaseRuntimeException e) {
        log.error("런타임 오류가 발생했습니다.", e);
        return ErrorResponse.toResponseEntity(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        // 예외 메시지 처리
        return new ResponseEntity<>("Validation error occurred", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
        BeanInstantiationException.class,
        MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> beanInstantiationExceptionHandler(Throwable t) {
        log.error(t.getCause().getMessage(), t);
        return ErrorResponse.toResponseEntity(
            HttpStatus.BAD_REQUEST,
            Errors.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(BaseRuntimeException e) {
        log.error(e.getErrorMessage(), e);

        return ErrorResponse.toResponseEntity(e);
    }
}
