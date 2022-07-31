package com.black.monkey.my.election.cmd.api.controller;

import com.black.monkey.my.election.cmd.domain.Message;
import com.black.monkey.my.election.commons.api.dto.BaseResponse;
import com.black.monkey.my.election.core.exceptions.AggregateNotFoundException;
import com.black.monkey.my.election.core.exceptions.CrvDoesntExistException;
import com.black.monkey.my.election.core.exceptions.PreviousVoteException;
import com.black.monkey.my.election.core.exceptions.UserWithoutCRVException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public void exception(Exception exception) {
        log.error("", exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public void exception(AggregateNotFoundException exception) {
        log.warn("{}", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public void exception(IllegalStateException exception) {
        log.warn("{}", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public void exception(CrvDoesntExistException exception) {
        log.error("{}", exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse> exception(PreviousVoteException exception) {
        log.warn("{}", exception.getMessage());
        return ResponseEntity.of(Optional.of(BaseResponse.builder().message(exception.getMessage()).build()));
    }


}
