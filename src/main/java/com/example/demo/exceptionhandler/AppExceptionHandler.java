/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.exceptionhandler;

import com.example.demo.apimanagement.ApiResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;
/**
 *
 * @author LUIS CASANOVA
 */

@ControllerAdvice
public class AppExceptionHandler {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){                
        Map<String, String> errorMap = new HashMap();
        
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        
        for (int i = 0; i < errors.size(); i++){
            errorMap.put(
                ((FieldError) errors.get(i)).getField(),
                errors.get(i).getDefaultMessage()
            );
        }
        
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        
        String errorDetails = "The method of the HTTP petition " + ex.getMethod() + " for the API " + 
                request.getDescription(false) + " is not supported.";
        String supportedMethods = " The supported methods are " + Arrays.toString(ex.getSupportedMethods()) + ".";
        
        ApiResponse response = new ApiResponse();
        response.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        response.setData(errorDetails + supportedMethods);
        response.setCodeStatus(-1);        
        response.setDate(LocalDateTime.now());
        
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
}
