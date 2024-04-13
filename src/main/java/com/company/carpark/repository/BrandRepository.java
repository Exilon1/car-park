package com.company.carpark.repository;

import com.company.carpark.datamodel.Brand;
import com.company.carpark.datamodel.Vehicle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

  List<Brand> findByVehicleIn(List<Vehicle> vehicles);

  List<Brand> findAllByOrderById();
}
