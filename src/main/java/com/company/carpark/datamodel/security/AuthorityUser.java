package com.company.carpark.datamodel.security;

import com.company.carpark.datamodel.Manager;
import java.util.Collections;
import java.util.Set;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class AuthorityUser extends User {

  private final Manager manager;

  public AuthorityUser(Manager manager) {
    super(manager.getUsername(), manager.getPassword(), manager.getRole() != null
        ? Set.of(new SimpleGrantedAuthority(manager.getRole())) : Collections.emptySet());
    this.manager = manager;
  }
}
