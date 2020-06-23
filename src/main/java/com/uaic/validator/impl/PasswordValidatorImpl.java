package com.uaic.validator.impl;

import org.springframework.stereotype.Service;

import com.uaic.domain.dto.PasswordDTO;
import com.uaic.exception.types.ValidationException;
import com.uaic.validator.PasswordValidator;

@Service
public class PasswordValidatorImpl implements PasswordValidator{

  @Override
  public void validateChangePassword(PasswordDTO forgotPasswordDTO) {
    if (forgotPasswordDTO.getToken() == null || forgotPasswordDTO.getToken().length() <= 0){
      throw new ValidationException("password.tokenNeeded", new Throwable());
    }
    if (forgotPasswordDTO.getPassword() == null || forgotPasswordDTO.getPassword().length() <= 0){
      throw new ValidationException("password.mandatory", new Throwable());
    }
    if (forgotPasswordDTO.getPasswordConfirmation() == null || forgotPasswordDTO.getPasswordConfirmation().length() <= 0){
      throw new ValidationException("password.confirmation.mandatory", new Throwable());
    }
    if (!forgotPasswordDTO.getPassword().equalsIgnoreCase(forgotPasswordDTO.getPasswordConfirmation())){
      throw new ValidationException("password.notMatching", new Throwable());
    }
  }

}
