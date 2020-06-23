package com.uaic.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uaic.builder.UserBuilder;
import com.uaic.domain.auth.User;
import com.uaic.domain.dto.UserDTO;
import com.uaic.exception.types.TechnicalException;
import com.uaic.repository.AuthorityRepository;
import com.uaic.repository.UserRepository;
import com.uaic.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserBuilder userBuilder;
  private final AuthorityRepository authorityRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, UserBuilder userBuilder, AuthorityRepository authorityRepository) {
    this.userRepository = userRepository;
    this.userBuilder = userBuilder;
    this.authorityRepository = authorityRepository;
  }

  @Override
  @Transactional
  public UserDTO createUser(UserDTO userDTO) {
    PasswordEncoder standardPasswordEncoder = new BCryptPasswordEncoder(11);
    User user = userBuilder.buildUser(userDTO);
    user.setPassword(standardPasswordEncoder.encode(user.getPassword()));
    return userBuilder.buildUserDTO(userRepository.save(user));
  }

  @Override
  @Transactional
  public UserDTO updateUser(Long id, UserDTO userDTO) {
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      throw new TechnicalException(String.format("Unable to update. No user with id %s exists!", id), new Throwable());
    }

    user.setEmail(userDTO.getEmail());
    user.setFullname(userDTO.getFullname());
    user.setUsername(userDTO.getUsername());
    user.setActivated(userDTO.isActivated());

    final User updatedUser = userRepository.save(user);
    return userBuilder.buildUserDTO(updatedUser);
  }

  @Override
  public Long identifyUser(String name) {
    return userRepository.findByUsername(name).getId();
  }

  @Override
  @Transactional
  public User findUser(String name) {
    return userRepository.findByUsername(name);
  }

  @Override
  @Transactional
  public Long signup(User user) {
    PasswordEncoder standardPasswordEncoder = new BCryptPasswordEncoder(11);
    user.getAuthorities().add(authorityRepository.findByNameIgnoreCase("ROLE_ADMIN"));
    user.setPassword(standardPasswordEncoder.encode(user.getPassword()));
    user.setActivated(true);
    userRepository.save(user);
    return user.getId();
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findByEmailIgnoreCase(email);
  }

  @Override
  public List<UserDTO> findAllUsers() {
    return userBuilder.buildUserDTOList(userRepository.findAll());
  }
}
