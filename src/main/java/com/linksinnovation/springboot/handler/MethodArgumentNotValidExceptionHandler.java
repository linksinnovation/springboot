package com.linksinnovation.springboot.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handler(MethodArgumentNotValidException ex) {
        Map<String, Object> map = new HashMap<>();
        List<Object> list = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            Map<String, Object> mapError = new HashMap<>();
            mapError.put("field", error.getField());
            mapError.put("message", error.getDefaultMessage());
            list.add(mapError);
        }
        map.put("errors", list);
        return map;
    }
}
