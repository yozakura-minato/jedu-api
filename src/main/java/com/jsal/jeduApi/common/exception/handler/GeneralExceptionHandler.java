package com.jsal.jeduApi.common.exception.handler;

import com.jsal.jeduApi.common.exception.BusinessLogicException;
import com.jsal.jeduApi.common.exception.ResourceConflictException;
import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.common.exception.SqlExecutionException;
import com.jsal.jeduApi.common.response.ApiResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Yozakura Minato
 */
@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    /**
     * @param exception MethodArgumentNotValidException
     * @return ResponseEntity with {@code 400 - BAD REQUEST} status and {@code message} ApiResponseBody
     */
    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseBody<?>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        String message = exception.getAllErrors().getFirst().getDefaultMessage();
        log.error("MethodValidationException with message: '{}'", message);
        String returnMessage = message != null ? message : "common.badRequest";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseBody.message(returnMessage));
    }

    /**
     * @param exception ResourceNotFoundException
     * @return ResponseEntity with {@code 404 - NOT FOUND} status and {@code message} ApiResponseBody
     */
    @ExceptionHandler(exception = ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseBody<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.error("ResourceNotFoundException with message: '{}'", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseBody.message(exception.getMessage()));
    }

    /**
     * @param exception ResourceConflictException
     * @return ResponseEntity with {@code 409 - CONFLICT} status and {@code message} ApiResponseBody
     */
    @ExceptionHandler(exception = ResourceConflictException.class)
    public ResponseEntity<ApiResponseBody<?>> handleResourceConflictException(ResourceConflictException exception) {
        log.error("ResourceConflictException with message: '{}'", exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponseBody.message(exception.getMessage()));
    }

    /**
     * @param exception BusinessLogicException
     * @return ResponseEntity with {@code 422 - UNPROCESSABLE CONTENT} status and {@code message} ApiResponseBody
     */
    @ExceptionHandler(exception = BusinessLogicException.class)
    public ResponseEntity<ApiResponseBody<?>> handleBusinessLogicException(BusinessLogicException exception) {
        log.error("BusinessLogicException with message: '{}'", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiResponseBody.message(exception.getMessage()));
    }

    /**
     * @param exception SqlExecutionException
     * @return ResponseEntity with {@code 500 - INTERNAL SERVER ERROR} status and {@code message} ApiResponseBody
     */
    @ExceptionHandler(exception = SqlExecutionException.class)
    public ResponseEntity<ApiResponseBody<?>> handleSqlExecutionException(SqlExecutionException exception) {
        log.error("SqlExecutionException with message: '{}'", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseBody.message("common.internalServerError"));
    }

    /**
     * @param exception RuntimeException
     * @return ResponseEntity with {@code 500 - INTERNAL SERVER ERROR} status and {@code message} ApiResponseBody
     */
    @ExceptionHandler(exception = RuntimeException.class)
    public ResponseEntity<ApiResponseBody<?>> handleRuntimeException(RuntimeException exception) {
        Throwable cause = exception.getCause();
        if (cause == null) {
            log.error("{} with message: '{}'", exception.getClass().getSimpleName(), exception.getMessage());
        } else {
            log.error("{} caused by {} with message: '{}'",
                    exception.getClass().getSimpleName(), cause.getClass().getSimpleName(), cause.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseBody.message("common.internalServerError"));
    }
}
