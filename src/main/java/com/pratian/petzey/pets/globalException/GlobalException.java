package com.pratian.petzey.pets.globalException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pratian.petzey.pets.exceptions.ParentNotFoundException;
import com.pratian.petzey.pets.exceptions.PetNotFoundException;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(ParentNotFoundException.class)
	public ResponseEntity<String> handleParentNotFound(ParentNotFoundException parentNotFoundException)
	{
		return new ResponseEntity<String>(parentNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PetNotFoundException.class)
	public ResponseEntity<String> handlePetNotFound(PetNotFoundException petNotFoundException)
	{
		return new ResponseEntity<String>(petNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}
	    @ExceptionHandler(MethodArgumentNotValidException.class)
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
			StringBuilder errors = new StringBuilder("Validation errors: ");
			ex.getBindingResult().getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
	 
			return ResponseEntity.badRequest().body(errors.toString());
		}
	
}
