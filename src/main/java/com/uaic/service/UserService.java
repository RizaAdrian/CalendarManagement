package com.uaic.service;

import java.util.List;

import com.uaic.domain.auth.User;
import com.uaic.domain.dto.UserDTO;

public interface UserService {

  UserDTO createUser(UserDTO userDTO);

  UserDTO updateUser(Long id, UserDTO userDTO);

  Long identifyUser(String name);

  User findUser(String name);

  Long signup(User user);

  User findUserByEmail(String email);

  List<UserDTO> findAllUsers();
}
