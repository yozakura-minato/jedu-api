package com.jsal.jeduApi.common.exception.handler;

import com.jsal.jeduApi.common.exception.BusinessLogicException;
import com.jsal.jeduApi.common.exception.ResourceConflictException;
import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.common.exception.SqlExecutionException;
import com.jsal.jeduApi.common.response.ApiResponse;
import com.jsal.jeduApi.common.util.CommonMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * @author Yozakura Minato
 */
@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    /**
     * @param exception HandlerMethodValidationException
     * @return ResponseEntity with 400 status and ApiResponse with error message body
     */
    @ExceptionHandler(exception = HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleHandlerMethodValidationException(
            HandlerMethodValidationException exception
    ) {
        String defaultMessage = exception.getAllErrors().getFirst().getDefaultMessage();
        String message = defaultMessage == null ? CommonMessage.BAD_REQUEST : defaultMessage;
        log.error("HandlerMethodValidationException with message: '{}'", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(message));
    }

    /**
     * @param exception ResourceNotFoundException
     * @return ResponseEntity with 404 status and ApiResponse with error message body
     */
    @ExceptionHandler(exception = ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.error("ResourceNotFoundException with message: '{}'", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(exception.getMessage()));
    }

    /**
     * @param exception ResourceConflictException
     * @return ResponseEntity with 409 status and ApiResponse with error message body
     */
    @ExceptionHandler(exception = ResourceConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceConflictException(ResourceConflictException exception) {
        log.error("ResourceConflictException with message: '{}'", exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(exception.getMessage()));
    }

    /**
     * @param exception BusinessLogicException
     * @return ResponseEntity with 422 status and ApiResponse with error message body
     */
    @ExceptionHandler(exception = BusinessLogicException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessLogicException(BusinessLogicException exception) {
        log.error("BusinessLogicException with message: '{}'", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiResponse.error(exception.getMessage()));
    }

    /**
     * @param exception SqlExecutionException
     * @return ResponseEntity with 500 status and ApiResponse with internal server error message body
     */
    @ExceptionHandler(exception = SqlExecutionException.class)
    public ResponseEntity<ApiResponse<?>> handleSqlExecutionException(SqlExecutionException exception) {
        log.error("SqlExecutionException with message: '{}'", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(CommonMessage.INTERNAL_SERVER_ERROR));
    }

    /**
     * @param exception RuntimeException
     * @return ResponseEntity with 500 status and ApiResponse with internal server error message body
     */
    @ExceptionHandler(exception = RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException exception) {
        Throwable cause = exception.getCause();
        if (cause == null) {
            log.error("{} with message: '{}'", exception.getClass().getSimpleName(), exception.getMessage());
        } else {
            log.error(
                    "{} caused by {} with message: '{}'",
                    exception.getClass().getSimpleName(), cause.getClass().getSimpleName(), cause.getMessage()
            );
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(CommonMessage.INTERNAL_SERVER_ERROR));
    }
}
