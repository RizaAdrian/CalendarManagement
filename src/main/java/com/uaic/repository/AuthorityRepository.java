package com.uaic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uaic.domain.auth.Authority;

import java.util.Set;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
  Authority findByNameIgnoreCase(String name);

  Set<Authority> findAll(Iterable<Long> ids);

  Set<Authority> findAll();
}
