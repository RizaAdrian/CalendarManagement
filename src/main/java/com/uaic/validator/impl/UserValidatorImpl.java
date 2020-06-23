package com.uaic.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uaic.domain.auth.User;
import com.uaic.domain.dto.UserDTO;
import com.uaic.exception.types.ValidationException;
import com.uaic.service.UserService;
import com.uaic.validator.UserValidator;

@Service
public class UserValidatorImpl implements UserValidator {
  
  private final UserService userService;
  
  @Autowired
  public UserValidatorImpl(UserService userService){
    this.userService = userService;
  }

  @Override
  public void validateSignup(UserDTO userDTO) {
    if (userDTO.getUsername() == null || userDTO.getUsername().length() <= 0){
      throw new ValidationException("user.username.notProvided", new Throwable());
    }
    if (userDTO.getFullname() == null || userDTO.getFullname().length() <= 0){
      throw new ValidationException("user.fullname.notProvided", new Throwable());
    }
    if (userDTO.getPassword()== null || userDTO.getPassword().length() <= 0){
      throw new ValidationException("user.password.notProvided", new Throwable());
    }
    if (userDTO.getEmail() == null || userDTO.getEmail().length() <= 0){
      throw new ValidationException("user.email.notProvided", new Throwable());
    }
    User user = userService.findUserByEmail(userDTO.getEmail());
    if (user != null && user.getId() != null){
      throw new ValidationException("user.email.alreadyUsed", new Throwable());
    }
    user = userService.findUser(userDTO.getUsername());
    if (user != null && user.getId() != null){
      throw new ValidationException("user.username.alreadyUsed", new Throwable());
    }
  }

}
