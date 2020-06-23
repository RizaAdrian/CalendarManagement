package com.uaic.service.impl;

import com.uaic.builder.AuthorityBuilder;
import com.uaic.domain.dto.AuthorityDTO;
import com.uaic.repository.AuthorityRepository;
import com.uaic.service.AuthorityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {

  private final AuthorityRepository authorityRepository;
  private final AuthorityBuilder authorityBuilder;

  @Autowired
  public AuthorityServiceImpl(AuthorityRepository authorityRepository, AuthorityBuilder authorityBuilder) {
    this.authorityRepository = authorityRepository;
    this.authorityBuilder = authorityBuilder;
  }

  @Override
  public Set<AuthorityDTO> findAllAuthorities() {
    return authorityBuilder.buildAuthorityDTOSet(authorityRepository.findAll());
  }
}
