package com.uaic.main.config.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uaic.domain.auth.Authority;
import com.uaic.domain.auth.User;
import com.uaic.exception.types.UserNotActiveException;
import com.uaic.repository.UserRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  private final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String credentials) {

    logger.info("Authenticating " + credentials + ".");
    String lowercaseCredentials = credentials.toLowerCase();

    User userFromDatabase;
    if (lowercaseCredentials.contains("@")) {
      userFromDatabase = userRepository.findByEmailIgnoreCase(lowercaseCredentials);
    } else {
      userFromDatabase = userRepository.findByUsernameIgnoreCase(lowercaseCredentials);
    }

    if (userFromDatabase == null) {
      throw new UsernameNotFoundException(
          "User " + lowercaseCredentials + " was not found in the database");
    } else if (!userFromDatabase.isActivated()) {
      throw new UserNotActiveException("User " + lowercaseCredentials + " is not activated");
    }

    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    for (Authority authority : userFromDatabase.getAuthorities()) {
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
      grantedAuthorities.add(grantedAuthority);
    }

    return new org.springframework.security.core.userdetails.User(userFromDatabase.getUsername(),
        userFromDatabase.getPassword(), grantedAuthorities);
  }

}
