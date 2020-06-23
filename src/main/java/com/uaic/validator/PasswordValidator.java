package com.uaic.validator;

import com.uaic.domain.dto.PasswordDTO;

public interface PasswordValidator {

  void validateChangePassword(PasswordDTO forgotPasswordDTO);

}
