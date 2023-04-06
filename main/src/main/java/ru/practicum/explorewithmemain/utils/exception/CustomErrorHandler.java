package ru.practicum.explorewithmemain.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithmemain.utils.exception.model.ApiError;

import java.util.Date;

@RestControllerAdvice()
public class CustomErrorHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(Exception e) {
        return new ApiError(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage()
        );
    }

    @ExceptionHandler(value = {ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(Exception e) {
        return new ApiError(
                new Date(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage()
        );
    }
}
