package com.example.demoquartz.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleException(Exception e, HttpServletRequest request) {
        //All exception logs here
        logger.error(e.getMessage(), e);

        Map<String, Object> data = toInternalExceptionMessage(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
    }

    /**
     *
     * @param exception
     * @param request
     * @return
     */
    public static Map<String, Object> toInternalExceptionMessage(Exception exception, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        data.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + ", " + exception.getMessage());
        data.put("path", request.getRequestURI());
        return data;
    }
}
