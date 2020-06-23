package com.uaic.service;

import java.util.Set;

import com.uaic.domain.dto.AuthorityDTO;

public interface AuthorityService {

  Set<AuthorityDTO> findAllAuthorities();
}
