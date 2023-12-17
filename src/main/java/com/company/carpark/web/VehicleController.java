package com.company.carpark.web;

import com.company.carpark.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

  @Autowired
  private VehicleService vehicleService;

  @GetMapping
  public String getAllVehicles(Model model) {
    model.addAttribute("vehicles", vehicleService.getAllVehicles());
    return "vehicles";
  }
}
