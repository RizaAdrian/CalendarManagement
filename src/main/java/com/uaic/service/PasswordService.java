package com.uaic.service;

import com.uaic.domain.dto.PasswordDTO;

public interface PasswordService {

  boolean forgotPassword(PasswordDTO forgotPasswordDTO);

  boolean resetPassword(PasswordDTO forgotPasswordDTO);

  void tokenCleanup();

}
