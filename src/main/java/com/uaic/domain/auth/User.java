package com.uaic.domain.auth;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "calendar_users")
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @SequenceGenerator(name = "calendar_authority_id_seq", sequenceName = "calendar_authority_id_seq", allocationSize = 1)
  private Long id;

  @Column(updatable = false, nullable = false)
  private String username;

  private String fullname;

  private String password;

  private String email;

  private boolean activated;

  @Column(name = "activationkey")
  private String activationKey;

  @Column(name = "resetpasswordkey")
  private String resetPasswordKey;

  @ManyToMany
  @JoinTable(name = "calendar_users_authority", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
  private Set<Authority> authorities = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "calendar_sharing_prestasie",
      joinColumns = @JoinColumn(name = "from_user_id"),
      inverseJoinColumns = @JoinColumn(name = "to_user_id")
  )
  private Set<User> sharingPrestasieUsers;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isActivated() {
    return activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

  public String getActivationKey() {
    return activationKey;
  }

  public void setActivationKey(String activationKey) {
    this.activationKey = activationKey;
  }

  public String getResetPasswordKey() {
    return resetPasswordKey;
  }

  public void setResetPasswordKey(String resetPasswordKey) {
    this.resetPasswordKey = resetPasswordKey;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

  public Set<User> getSharingPrestasieUsers() {
    return sharingPrestasieUsers;
  }

  public void setSharingPrestasieUsers(Set<User> sharingPrestasieUsers) {
    this.sharingPrestasieUsers = sharingPrestasieUsers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    User user = (User) o;

    if (!username.equals(user.username))
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return this.username.hashCode();
  };

  @Override
  public String toString() {
    return "User{" + "username='" + username + '\'' + ", email='" + email + '\'' + ", activated='"
        + activated + '\'' + ", activationKey='" + activationKey + '\'' + ", resetPasswordKey='"
        + resetPasswordKey + '\'' + ", authorities=" + authorities + '}';
  }
}
