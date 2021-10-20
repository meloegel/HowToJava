package com.howto.services;


import com.howto.models.ValidationError;

import java.util.List;

public interface HelperFunctions {
 List<ValidationError> getConstraintViolation(Throwable cause);

 boolean isAuthorizedToMakeChange(String username);
}
