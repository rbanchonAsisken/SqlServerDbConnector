package com.example.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
        }
        return sw.toString();
    }

    private Map<String, Object> buildBody(Throwable ex, HttpStatus status) {
        return new LinkedHashMap<>() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            put("transaccion", false);
            put("errorCode", status);
            put("errorType", ex.getClass());
            put("errorMessage", ex.getMessage());
            put("errorTrace", getStackTraceAsString(ex));
        }};
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSqlException(SQLException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(buildBody(ex, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(SQLException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(buildBody(ex, HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
