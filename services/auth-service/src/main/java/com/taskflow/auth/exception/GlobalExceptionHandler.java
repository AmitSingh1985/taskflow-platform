package com.taskflow.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.taskflow.common.api.ErrorResponse;
import com.taskflow.common.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	//private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {

		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Business Error", ex.getMessage(),
				request.getRequestURI());

		return ResponseEntity.badRequest().body(error);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		String message = ex.getBindingResult().getFieldError().getDefaultMessage();

		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Error", message,
				request.getRequestURI());

		return ResponseEntity.badRequest().body(error);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {

		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
				ex.getMessage(), request.getRequestURI());
		String methodName = null;
		String className = null;
		if (ex.getStackTrace().length > 0) {
			methodName = ex.getStackTrace()[0].getMethodName();
			className = ex.getStackTrace()[0].getMethodName();
			// Returns full package path, e.g., com.example.service.UserService
		}
		log.error("The Class" + " " + className + " Throws Exception for method " + " " + methodName + " ", ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

	}

}