package by.semargl.controller.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import by.semargl.aspect.LoggingAspect;
import by.semargl.exception.NoSuchEntityException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandlers {

    private static final Logger log = Logger.getLogger(LoggingAspect.class);

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<ErrorMessage> handleNoSuchEntityException(NoSuchEntityException e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(2L, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(JsonMappingException e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(3L, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(4L, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleUsernameNotFoundException(AuthenticationException e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(5L, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
