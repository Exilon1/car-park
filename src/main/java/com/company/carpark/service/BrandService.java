package com.company.carpark.service;

import com.company.carpark.datamodel.Brand;
import com.company.carpark.datamodel.Vehicle;
import com.company.carpark.repository.BrandRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

  @Autowired
  private BrandRepository brandRepository;

  public List<Brand> getAllBrands() {
    return brandRepository.findAll();
  }

  public List<Brand> getBrandByVehicleIn(List<Vehicle> vehicles) {
    return brandRepository.findByVehicleIn(vehicles);
  }

}
