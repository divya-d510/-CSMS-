package com.cars24.csms.advice;

import com.cars24.csms.data.res.ApiRes;
import com.cars24.csms.exceptions.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j

public class GlobalExceptionHandler {

    //any errors like validation or verification errors while taking an input then we directly go to GlobalException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiRes> handleValidationExceptions(MethodArgumentNotValidException exception)
    {
        log.info("[handleValidationExceptions]");

        Map<String,String> errorMap=new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->
        {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });

        ApiRes ApiRes=new ApiRes();
        ApiRes.setStatusCode(HttpStatus.BAD_REQUEST.value());
        ApiRes.setSuccess(false);
        ApiRes.setMessage("Validation failed");
        ApiRes.setService("APPUSER "+HttpStatus.BAD_REQUEST.value());
        ApiRes.setData(errorMap);
        return ResponseEntity.badRequest().body(ApiRes);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ApiRes> handleUserServiceExceptions(UserServiceException exception)
    {
        ApiRes ApiRes=new ApiRes();
        ApiRes.setStatusCode(HttpStatus.BAD_REQUEST.value());
        ApiRes.setSuccess(false);
        ApiRes.setMessage("Invalid signUp data");
        ApiRes.setService("APPUSER "+HttpStatus.BAD_REQUEST.value());
        //ApiRes.setData(null);
        return ResponseEntity.ok().body(ApiRes);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidDateFormat(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }



}