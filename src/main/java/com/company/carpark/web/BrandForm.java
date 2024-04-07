package com.company.carpark.web;

import com.company.carpark.datamodel.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BrandForm {

  private Brand brand;

}
