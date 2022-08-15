package com.uis.authorization.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.uis.authorization.dto.ErrorDTO;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class ControllerException extends Exception{
    private static final Logger log = LoggerFactory.getLogger(ControllerException.class);
    private static final long serialVersionUID = 4581791676057366467L;
    private static final String TIPO_WARNING = "warning";
    private static final String TIPO_ERROR = "error";
    private static final String USUARIO_DATASOURCE_DEFAULT = "develop";

    public ControllerException() {
    }

    @ExceptionHandler({AccessDeniedException.class, AuthorizationServiceException.class})
    public ResponseEntity<ErrorDTO> handleSpringSecurityException(Exception e, HttpServletResponse response, HttpServletRequest request) {
        HttpStatus codigoHttp = HttpStatus.FORBIDDEN;
        return new ResponseEntity(new ErrorDTO(codigoHttp.value(), codigoHttp.getReasonPhrase(), "warning", request.getRequestURI(), false), codigoHttp);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDTO> invalidoObjetoDeEntrada(HttpServletRequest request, MethodArgumentNotValidException e) {
        HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();
        fieldErrors.forEach((f) -> {
            String var10001 = f.getField();
            errorMessage.append(var10001 + " " + f.getDefaultMessage() + " ");
        });
        return new ResponseEntity(new ErrorDTO(codigoHttp.value(), errorMessage.toString(), "warning", request.getRequestURI(), false), codigoHttp);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorDTO> invalidoConversionTipoDato(HttpServletRequest request, MethodArgumentTypeMismatchException e, HandlerMethod handlerMethod) {
        HttpStatus codigoHttp = HttpStatus.METHOD_NOT_ALLOWED;
        return new ResponseEntity(new ErrorDTO(codigoHttp.value(), "Tipo de dato asignado incorrectamente", "error", request.getRequestURI(), false), codigoHttp);
    }

    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class, HttpMessageNotReadableException.class, InvalidFormatException.class, HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ErrorDTO> transaccionNoExitosaIncumpleValidacionesMapeo(HttpServletRequest request, Exception e) {
        HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
        return new ResponseEntity(new ErrorDTO(codigoHttp.value(), "Valores inválidos en la base de datos", "error", request.getRequestURI(), false), codigoHttp);
    }

    @ExceptionHandler({org.hibernate.exception.ConstraintViolationException.class, TransientPropertyValueException.class, org.hibernate.exception.ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, Exception.class, PersistenceException.class, InvalidDataAccessApiUsageException.class, GenericJDBCException.class})
    public ResponseEntity<ErrorDTO> transaccionNoExitosaHibernateYBaseDatos(HttpServletRequest request, Exception e, HandlerMethod handlerMethod) {
        HttpStatus codigoHttp = HttpStatus.CONFLICT;
        e.printStackTrace();
        return new ResponseEntity(new ErrorDTO(codigoHttp.value(), "Valores inválidos de la base de datos", "error", request.getRequestURI(), false), codigoHttp);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ErrorDTO> nullPointerException(HttpServletRequest request, NullPointerException e, HandlerMethod handlerMethod) {
        return new ResponseEntity(new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valores nulos no permitidos", "error", request.getRequestURI(), false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorDTO> incumpleValidacionNegocio(HttpServletRequest request, ValidationException e) {
        HttpStatus codigoHttp = HttpStatus.CONFLICT;
        return e.getCause() != null && e.getCause().getMessage().equals("true") ? new ResponseEntity(new ErrorDTO(codigoHttp.value(), e.getMessage(), "warning", request.getRequestURI(), true), codigoHttp) : new ResponseEntity(new ErrorDTO(codigoHttp.value(), e.getMessage(), "warning", request.getRequestURI(), false), codigoHttp);
    }

    @ExceptionHandler({TransactionException.class, TechnicalException.class, IOException.class})
    public ResponseEntity<ErrorDTO> transaccioNoExitosaOErrorTecnicoAtrapado(Exception e, HandlerMethod handlerMethod) {
        HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
        return new ResponseEntity(new ErrorDTO(codigoHttp.value(), e.getMessage(), "error", (String)null, false), codigoHttp);
    }

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<ErrorDTO> dataNotFound(DataNotFoundException e, HandlerMethod handlerMethod) {
        HttpStatus codigoHttp = HttpStatus.NOT_FOUND;
        return new ResponseEntity(new ErrorDTO(codigoHttp.value(), e.getMessage(), "error", (String)null, false), codigoHttp);
    }
}
