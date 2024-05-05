package com.company.carpark.repository;

import com.company.carpark.datamodel.Manager;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

  List<Manager> findAllByOrderByUsername();

  Manager findByUsername(String username);
}
