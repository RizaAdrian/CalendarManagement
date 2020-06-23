package com.uaic.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uaic.builder.UserBuilder;
import com.uaic.domain.dto.UserDTO;
import com.uaic.service.UserService;
import com.uaic.validator.UserValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class IdentityController {

  private final Logger logger = LoggerFactory.getLogger(IdentityController.class);

  private final UserService userService;
  private final UserBuilder userBuilder;
  private final UserValidator userValidator;
  private final TokenStore tokenStore;


  @Autowired
  public IdentityController(UserService userService, UserBuilder userBuilder,
      UserValidator userValidator, TokenStore tokenStore) {
    this.userService = userService;
    this.userBuilder = userBuilder;
    this.userValidator = userValidator;
    this.tokenStore = tokenStore;
  }

  @ApiOperation(value = "Read user identity", notes = "Read user identity",
      response = UserDTO.class)
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Error based on request!"),
      @ApiResponse(code = 500, message = "Error based on internal server behavior!")})
  @ResponseStatus(value = HttpStatus.OK)
  @RequestMapping(value = "/api/identity", method = RequestMethod.GET, produces = "application/json")
  public UserDTO identity(Principal principal) {
    logger.info("Called identity(" + principal.getName() + ") service.");
    return userBuilder.buildUserDTO(userService.findUser(principal.getName()));
  }

  @ApiOperation(value = "Signup", notes = "Signup", response = Long.class)
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Error based on request!"),
      @ApiResponse(code = 500, message = "Error based on internal server behavior!")})
  @ResponseStatus(value = HttpStatus.OK)
  @RequestMapping(value = "/public/signup", method = RequestMethod.POST, produces = "application/json")
  public Long sigup(@RequestBody UserDTO userDTO) {
    logger.info("Called signup(" + userDTO.getUsername() + ") service.");
    userValidator.validateSignup(userDTO);
    return userService.signup(userBuilder.buildUser(userDTO));
  }

  @RequestMapping(value = "/api/oauth/logout", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public void logout(HttpServletRequest request, Principal principal) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null) {
      String tokenValue = authHeader.replace("Bearer", "").trim();
      OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
      tokenStore.removeAccessToken(accessToken);
    }
  }
}
