package com.uaic.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uaic.domain.auth.ForgotPassword;
import com.uaic.domain.auth.User;
import com.uaic.domain.dto.PasswordDTO;
import com.uaic.exception.types.ValidationException;
import com.uaic.repository.ForgotPasswordRepository;
import com.uaic.repository.UserRepository;
import com.uaic.service.NotificationService;
import com.uaic.service.PasswordService;

@Service
public class PasswordServiceImpl implements PasswordService {

  private final UserRepository userRepository;
  private final ForgotPasswordRepository forgotPasswordRepository;
  private final NotificationService notificationService;

  @Autowired
  public PasswordServiceImpl(UserRepository userRepository,
                             ForgotPasswordRepository forgotPasswordRepository, NotificationService notificationService) {
    this.userRepository = userRepository;
    this.forgotPasswordRepository = forgotPasswordRepository;
    this.notificationService = notificationService;
  }

  @Override
  public boolean forgotPassword(PasswordDTO forgotPasswordDTO) {
    User user = getUser(forgotPasswordDTO.getUsername());
    if (user != null) {
      ForgotPassword forgotPassword = createForgotPassword(user.getEmail());
      notificationService.sendForgotPasswordNotification(forgotPassword.getUserToken(),
          user.getEmail());
      return true;
    } else {
      throw new ValidationException("user.notFound", new Throwable());
    }
  }

  @Override
  public boolean resetPassword(PasswordDTO forgotPasswordDTO) {
    ForgotPassword forgotPassword =
        forgotPasswordRepository.findByUserToken(forgotPasswordDTO.getToken());
    if (forgotPassword != null && forgotPassword.getId() != null && !forgotPassword.isChanged()) {
      updateUser(forgotPasswordDTO, forgotPassword);
      forgotPassword.setChanged(true);
      forgotPassword.setChangeDate(new Date());
      forgotPasswordRepository.save(forgotPassword);
      return true;
    } else {
      throw new ValidationException("user.tokenExpired", new Throwable());
    }
  }

  @Transactional
  private void updateUser(PasswordDTO forgotPasswordDTO, ForgotPassword forgotPassword) {
    User user = forgotPassword.getUser();
    StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
    user.setPassword(standardPasswordEncoder.encode(forgotPasswordDTO.getPassword()));
    userRepository.saveAndFlush(user);
  }

  @Transactional
  private ForgotPassword createForgotPassword(String email) {
    ForgotPassword forgotPassword = new ForgotPassword();
    User user = getUser(email);
    forgotPassword.setUser(user);
    forgotPassword.setUserToken(UUID.randomUUID().toString());
    forgotPassword.setSent(true);
    forgotPassword.setSendDate(new Date());
    forgotPasswordRepository.save(forgotPassword);
    return forgotPassword;
  }

  private User getUser(String email) {
    if (email.contains("@")) {
      return userRepository.findByEmailIgnoreCase(email);
    } else {
      return userRepository.findByUsername(email);
    }
  }

  @Override
  public void tokenCleanup() {
    // TODO Auto-generated method stub
  }

}
