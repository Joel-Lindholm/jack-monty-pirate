package com.pirate.jackmonty.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controller advice that handles BadNbrOfIslandsExceptions.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class MontyExceptionHandler {

    @ExceptionHandler(BadNbrOfIslandsException.class)
    public final ResponseEntity<Object> handle(BadNbrOfIslandsException badNbrOfIslandsException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Make sure that the excluded island is not null and is less than N - 2 where N is the number of islands ");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
