package com.uaic.builder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.uaic.domain.auth.User;
import com.uaic.domain.dto.UserDTO;

@Component
public class UserBuilder {

  public UserDTO buildUserDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setEmail(user.getEmail());
    userDTO.setFullname(user.getFullname());
    userDTO.setUsername(user.getUsername());
    userDTO.setActivated(user.isActivated());
    return userDTO;
  }

  public User buildUser(UserDTO userDTO) {
    User user = new User();
    user.setEmail(userDTO.getEmail());
    user.setFullname(userDTO.getFullname());
    user.setUsername(userDTO.getUsername());
    user.setPassword(userDTO.getPassword());
    user.setActivated(userDTO.isActivated());
    return user;
  }

  public List<UserDTO> buildUserDTOList(List<User> users) {
    return users.stream().map(user -> buildUserDTO(user)).collect(Collectors.toList());
  }
}
