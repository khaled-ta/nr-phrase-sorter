package com.kta.newrelic.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class FileLoadingException extends Exception {

    public FileLoadingException(String errorMessage, Exception e) {
        super(errorMessage, e);
        log.error(ExceptionUtils.getMessage(e));
        log.trace(ExceptionUtils.getStackTrace(e)); // this is just to demonstrate how to reduce noise to end-user
    }

}
