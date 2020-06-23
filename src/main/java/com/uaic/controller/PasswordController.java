package com.uaic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uaic.domain.dto.PasswordDTO;
import com.uaic.service.PasswordService;
import com.uaic.validator.PasswordValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class PasswordController {

  private final Logger logger = LoggerFactory.getLogger(PasswordController.class);

  private final PasswordService forgotPasswordService;
  private final PasswordValidator passwordValidator;

  @Autowired
  public PasswordController(PasswordService forgotPasswordService,
      PasswordValidator passwordValidator) {
    this.forgotPasswordService = forgotPasswordService;
    this.passwordValidator = passwordValidator;
  }

  @ApiOperation(value = "Forgot Password", notes = "Forgot Password", response = Boolean.class)
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Error based on request!"),
      @ApiResponse(code = 500, message = "Error based on internal server behavior!")})
  @ResponseStatus(value = HttpStatus.OK)
  @RequestMapping(value = "/public/requestpasswordtoken", method = RequestMethod.POST,
      produces = "application/json")
  public boolean requestPasswordToken(@RequestBody PasswordDTO forgotPasswordDTO) {
    logger.info("Called requestPasswordToken(" + forgotPasswordDTO.getUsername() + ").");
    return forgotPasswordService.forgotPassword(forgotPasswordDTO);
  }

  @ApiOperation(value = "Reset Password", notes = "Reset Password", response = Boolean.class)
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Error based on request!"),
      @ApiResponse(code = 500, message = "Error based on internal server behavior!")})
  @ResponseStatus(value = HttpStatus.OK)
  @RequestMapping(value = "/public/resetpassword/{resetToken}", method = RequestMethod.POST,
      produces = "application/json")
  public boolean resetPassword(@RequestBody PasswordDTO forgotPasswordDTO,
      @ApiParam(name = "resetToken", value = "String containing reset token",
          required = true) @PathVariable("resetToken") String resetToken) {
    logger.info("Called resetPassword(" + resetToken + ").");
    passwordValidator.validateChangePassword(forgotPasswordDTO);
    return forgotPasswordService.resetPassword(forgotPasswordDTO);
  }
}
