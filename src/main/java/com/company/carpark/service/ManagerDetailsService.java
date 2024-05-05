package com.company.carpark.service;

import com.company.carpark.datamodel.Manager;
import com.company.carpark.datamodel.security.AuthorityUser;
import com.company.carpark.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ManagerDetailsService implements UserDetailsService {

  @Autowired
  private ManagerRepository managerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Manager manager = managerRepository.findByUsername(username);

    if (manager == null) {
      throw new UsernameNotFoundException(username);
    }
    return new AuthorityUser(manager);
  }
}
