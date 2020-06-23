package com.uaic.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.uaic.service.PasswordService;

@Component
public class ForgotPasswordCleanup {
  
  private final PasswordService forgotPasswordService;

  @Autowired
  public ForgotPasswordCleanup(PasswordService forgotPasswordService){
    this.forgotPasswordService = forgotPasswordService;
  }
  
  //@Scheduled(cron = "*/20 * * * * ? ") - for testing; every 20 seconds
  @Scheduled(cron="0 0 * * * *")
  public void forgotPasswordCron() {
    forgotPasswordService.tokenCleanup();
  }
}
