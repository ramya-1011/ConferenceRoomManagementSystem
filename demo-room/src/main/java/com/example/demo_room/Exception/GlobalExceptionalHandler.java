//package com.example.demo_room.Exception;
//
//
//import com.example.demo_room.dto.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestControllerAdvice
//@ControllerAdvice
//public class GlobalExceptionalHandler {
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Response> handleRunTimeException(RuntimeException ex) {
//        Response response = new Response( ex.getMessage(), false);
//        return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST);
//    }
//
////    public ResponseEntity<String> handleMyException(MyException ex) {
////        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
////    }
//@ExceptionHandler(MyException.class)
//    public ResponseEntity<Response> MyExceptionHandler(MyException ex) {
//        String message = ex.getMessage();
//        Response response = new Response( message, false);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, Object>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("message", "Please check the details and try again.");
//
//        Map<String, List<String>> modelState = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            modelState.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(message);
//        });
//
//        errorResponse.put("errorState", modelState);
//        errorResponse.put("status", Boolean.FALSE);
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//}
package com.example.demo_room.Exception;

import com.example.demo_room.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
//@ControllerAdvice
public class GlobalExceptionalHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionalHandler.class);

    @ExceptionHandler(MyException.class)
    public ResponseEntity<Response> handleMyException(MyException ex) {
        logger.error("MyException occurred: {}", ex.getMessage());
        Response response = new Response(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}