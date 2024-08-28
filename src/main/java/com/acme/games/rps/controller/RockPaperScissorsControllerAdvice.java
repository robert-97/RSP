package com.acme.games.rps.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RockPaperScissorsControllerAdvice {
    private static final Logger logger = LogManager.getLogger(RockPaperScissorsControllerAdvice.class);

    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        logger.error("Handled IllegalStateException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your session was expired, please start a new game.");
    }

}
