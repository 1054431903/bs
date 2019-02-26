package com.fwk.service.pusher.controller.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class Handler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public String exception(MethodArgumentNotValidException exception) {
		return exception.getBindingResult().getAllErrors().stream().map(e -> e.getDefaultMessage()).sorted().collect(Collectors.toList()).get(0);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
	public String exception(Exception exception) {
		return exception.getMessage();
	}

}
