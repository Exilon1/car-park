package com.company.carpark.web;

import com.company.carpark.datamodel.Brand;
import com.company.carpark.datamodel.Vehicle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleAndBrandForm {

  private Vehicle vehicle;
  private Brand brand;

}
