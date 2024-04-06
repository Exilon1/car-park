package com.company.carpark.web;

import com.company.carpark.datamodel.Brand;
import com.company.carpark.datamodel.Vehicle;
import com.company.carpark.repository.BrandRepository;
import com.company.carpark.repository.VehicleRepository;
import com.company.carpark.service.BrandService;
import com.company.carpark.service.VehicleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class VehicleController {

  @Autowired
  private VehicleService vehicleService;
  @Autowired
  private BrandService brandService;

  @Autowired
  private VehicleRepository vehicleRepository;
  @Autowired
  private BrandRepository brandRepository;

  @GetMapping("/vehicles")
  public String getAllVehicles(Model model) {
    List<Vehicle> vehicles = vehicleService.getAllVehicles();
    List<Brand> brands = brandService.getBrandByVehicleIn(vehicles);
    model.addAttribute("vehicles", vehicles);
    model.addAttribute("brands", brands);

    return "vehicles";
  }

  @GetMapping("/brands")
  public String showBrandList(Model model) {
    model.addAttribute("brands", brandService.getAllBrands());
    return "brands";
  }

  @GetMapping("/editVehicle/{id}")
  public String showEditForm(@PathVariable Long id, Model model) {
    Vehicle vehicle = vehicleRepository.findById(id).orElse(null);

    if (vehicle != null) {
      Brand brand = vehicle.getBrand();

      if (brand == null) {
        brand = new Brand();
        brand.setVehicle(vehicle);
      }

      VehicleAndBrandForm form = new VehicleAndBrandForm();
      form.setVehicle(vehicle);
      form.setBrand(brand);

      model.addAttribute("form", form);
    }

    return "edit-vehicle";
  }

  @GetMapping("/deleteVehicle/{id}")
  public String deleteVehicle(@PathVariable Long id) {
    vehicleRepository.deleteById(id);

    return "redirect:/vehicles";
  }

  @PostMapping("/addVehicleAndBrand")
  public String addVehicleAndBrand(Model model) {
    VehicleAndBrandForm form = new VehicleAndBrandForm();

    Vehicle vehicle = new Vehicle();
    Brand brand = new Brand();

    form.setVehicle(vehicle);
    form.setBrand(brand);

    model.addAttribute("form", form);

    return "edit-vehicles";
  }

  @Transactional
  @PostMapping("/saveVehicleAndBrand")
  public String saveVehicleAndBrand(@ModelAttribute("form") VehicleAndBrandForm form) {
    Vehicle vehicle = form.getVehicle();
    Brand brand = form.getBrand();

    vehicleRepository.save(vehicle);
    brand.setVehicle(vehicle);
    brandRepository.save(brand);

    return "redirect:/vehicles";
  }
}
