package by.semargl.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOthersException(Exception e) {
        /* Handles all other exceptions. Status code 500. */
//        log.error(e.getMessage(), e);
//        log.info(e.getMessage(), e);
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(2L, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
