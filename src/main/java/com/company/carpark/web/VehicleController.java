package com.company.carpark.web;

import com.company.carpark.datamodel.Brand;
import com.company.carpark.datamodel.Driver;
import com.company.carpark.datamodel.Enterprise;
import com.company.carpark.datamodel.Vehicle;
import com.company.carpark.repository.BrandRepository;
import com.company.carpark.repository.DriversRepository;
import com.company.carpark.repository.EnterpriseRepository;
import com.company.carpark.repository.VehicleRepository;
import com.company.carpark.service.BrandService;
import com.company.carpark.service.VehicleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
  @Autowired
  private EnterpriseRepository enterpriseRepository;
  @Autowired
  private DriversRepository driversRepository;

  @ResponseBody
  @GetMapping("/json-vehicles")
  public List<Vehicle> getJsonVehicles() {
    return vehicleRepository.findAllByOrderById();
  }

  @ResponseBody
  @GetMapping("/json-brands")
  public List<Brand> getJsonBrands() {
    return brandRepository.findAllByOrderById();
  }

  @ResponseBody
  @GetMapping("/json-drivers")
  public List<Driver> getJsonDrivers() {
    return driversRepository.findAllByOrderById();
  }

  @ResponseBody
  @GetMapping("/json-enterprise")
  public List<Enterprise> getJsonEnterprise() {
    return enterpriseRepository.findAllByOrderById();
  }

  @GetMapping("/vehicles")
  public String getAllVehicles(Model model) {
    List<Vehicle> vehicles = vehicleRepository.findAllByOrderById();
    List<Brand> brands = brandRepository.findByVehicleIn(vehicles);
    model.addAttribute("vehicles", vehicles);
    model.addAttribute("brands", brands);

    return "vehicles";
  }

  @GetMapping("/brands")
  public String showBrandList(Model model) {
    model.addAttribute("brands", brandRepository.findAll());
    return "brands";
  }

  @GetMapping("/editVehicle/{id}")
  public String showEditVehicleForm(@PathVariable Long id, Model model) {
    Vehicle vehicle = vehicleRepository.findById(id).orElse(null);
    List<Brand> brands = brandRepository.findAll();

    if (vehicle != null) {
      VehicleAndBrandForm form = new VehicleAndBrandForm();
      form.setVehicle(vehicle);
      form.setBrands(brands);

      model.addAttribute("form", form);
    }

    return "edit-vehicle";
  }

  @GetMapping("/editBrand/{id}")
  public String showEditBrandForm(@PathVariable Long id, Model model) {
    Brand brand = brandRepository.findById(id).orElse(null);

    if (brand != null) {
      BrandForm brandForm = BrandForm.builder().brand(brand).build();
      model.addAttribute("form", brandForm);
    }

    return "edit-brand";
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
    vehicle.setBrand(brand);

    form.setVehicle(vehicle);

    model.addAttribute("form", form);

    return "create-vehicle_with_brands";
  }

  @PostMapping("/saveVehicleAndBrand")
  public String saveVehicleAndBrand(@ModelAttribute("form") VehicleAndBrandForm form) {
    Vehicle vehicle = form.getVehicle();
    Brand brand = vehicle.getBrand();

    vehicleRepository.save(vehicle);
    brand.setVehicle(vehicle);
    brandRepository.save(brand);

    return "redirect:/vehicles";
  }

  @PostMapping("/saveBrand")
  public String saveBrand(@ModelAttribute("form") BrandForm form) {
    brandRepository.save(form.getBrand());

    return "redirect:/brands";
  }
}
