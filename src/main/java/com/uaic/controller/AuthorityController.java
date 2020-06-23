package com.uaic.controller;

import com.uaic.domain.dto.AuthorityDTO;
import com.uaic.service.AuthorityService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/authorities")
public class AuthorityController {

  private final Logger logger = LoggerFactory.getLogger(AuthorityController.class);

  private final AuthorityService authorityService;

  @Autowired
  public AuthorityController(AuthorityService authorityService) {
    this.authorityService = authorityService;
  }

  @ApiOperation(value = "List authorities", notes = "List authorities", response = List.class)
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Error based on request!"),
      @ApiResponse(code = 500, message = "Error based on internal server behavior!")})
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Set<AuthorityDTO> listAuthorities() {
    logger.info("Called listUsers().");
    return authorityService.findAllAuthorities();
  }
}
