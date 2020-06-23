package com.uaic.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uaic.domain.auth.ForgotPassword;

@Repository
public interface ForgotPasswordRepository extends CrudRepository<ForgotPassword, Long> {

  ForgotPassword findByUserToken(String resetToken);

  @Modifying(clearAutomatically = true)
  @Query(value = "DELETE FROM ForgotPassword fp WHERE fp.changed = 0 AND fp.sendDate < ?1")
  void deleteUnusedByChangeDate(Date changeDate);

}
