package com.company.carpark.repository;

import com.company.carpark.datamodel.Driver;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriversRepository extends JpaRepository<Driver, Long> {

  List<Driver> findAllByOrderById();
}
