package com.murglin.consulting.publishinghousereviewservice.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final void handleAllExceptions(Exception ex) {
        log.error("An exception has occurred", ex);
        //TODO publish some metrics with evaluated time and failures to the monitoring solution eg: New Relic
        //TODO we should map exception to some error codes and http status codes here + error messages
    }
}
