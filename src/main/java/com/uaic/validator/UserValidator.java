package com.uaic.validator;

import com.uaic.domain.dto.UserDTO;

public interface UserValidator {

  void validateSignup(UserDTO userDTO);

}
