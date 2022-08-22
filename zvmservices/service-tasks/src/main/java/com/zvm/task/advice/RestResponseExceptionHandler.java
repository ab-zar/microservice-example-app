package com.zvm.task.advice;

import com.zvm.clients.feature.FeatureNotFoundException;
import com.zvm.clients.task.TaskNotFoundException;
import com.zvm.clients.user.UserNotFoundException;
import com.zvm.task.dto.ExceptionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleError(Exception ex) {
        return ResponseEntity.badRequest().body(new ExceptionDto(ex.getMessage()));
    }
}
