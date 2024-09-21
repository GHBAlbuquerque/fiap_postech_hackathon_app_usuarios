package com.fiap.hackathon.common.exceptions.custom;

import com.fiap.hackathon.common.exceptions.model.CustomError;
import com.fiap.hackathon.common.exceptions.model.CustomException;

import java.util.List;

public class UserDeactivationException extends CustomException {

    public UserDeactivationException(ExceptionCodes code, String message) {
        super(code, message);
    }

    public UserDeactivationException(ExceptionCodes code, String message, List<CustomError> customErrors) {
        super(code, message, customErrors);
    }
}