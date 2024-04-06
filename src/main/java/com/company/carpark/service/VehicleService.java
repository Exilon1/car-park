package com.company.carpark.service;

import com.company.carpark.datamodel.Vehicle;
import com.company.carpark.repository.VehicleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

  @Autowired
  private VehicleRepository vehicleRepository;

  public List<Vehicle> getAllVehicles() {
    return vehicleRepository.findAllByOrderById();
  }
}
