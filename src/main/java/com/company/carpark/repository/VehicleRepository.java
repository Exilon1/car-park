package com.company.carpark.repository;

import com.company.carpark.datamodel.Vehicle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

  List<Vehicle> findAllByOrderById();
}
