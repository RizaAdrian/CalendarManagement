package com.uaic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

  private final Logger logger = LoggerFactory.getLogger(StatusController.class);

  @ResponseStatus(value = HttpStatus.OK)
  @RequestMapping(value = "/alive", method = RequestMethod.GET, produces = "application/json")
  public String alive() {
    logger.info("Called alive().");
    return "Alive!";
  }

}
