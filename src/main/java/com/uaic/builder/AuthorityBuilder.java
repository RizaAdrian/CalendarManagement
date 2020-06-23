package com.uaic.builder;

import org.springframework.stereotype.Component;

import com.uaic.domain.auth.Authority;
import com.uaic.domain.dto.AuthorityDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorityBuilder {
  public AuthorityDTO buildAuthorityDTO(Authority authority) {
    AuthorityDTO authorityDTO = new AuthorityDTO();
    authorityDTO.setId(authority.getId());
    authorityDTO.setName(authority.getName());
    return authorityDTO;
  }

  public Authority buildAutority(AuthorityDTO authorityDTO) {
    Authority authority = new Authority();
    authority.setId(authorityDTO.getId());
    authority.setName(authorityDTO.getName());
    return authority;
  }

  public Set<AuthorityDTO> buildAuthorityDTOSet(Set<Authority> authorities) {
    return authorities.stream().map(authority -> buildAuthorityDTO(authority)).collect(Collectors.toSet());
  }
}
