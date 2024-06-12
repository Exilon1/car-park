package com.company.carpark.web;

import com.company.carpark.datamodel.Brand;
import com.company.carpark.datamodel.Driver;
import com.company.carpark.datamodel.Enterprise;
import com.company.carpark.datamodel.Manager;
import com.company.carpark.datamodel.Vehicle;
import com.company.carpark.datamodel.security.AuthorityUser;
import com.company.carpark.exception.PermissionException;
import com.company.carpark.repository.BrandRepository;
import com.company.carpark.repository.DriversRepository;
import com.company.carpark.repository.EnterpriseRepository;
import com.company.carpark.repository.ManagerRepository;
import com.company.carpark.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class CommonController {

  @Autowired
  private VehicleRepository vehicleRepository;
  @Autowired
  private BrandRepository brandRepository;
  @Autowired
  private EnterpriseRepository enterpriseRepository;
  @Autowired
  private DriversRepository driversRepository;
  @Autowired
  private ManagerRepository managerRepository;

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

  @ResponseBody
  @GetMapping("/json-managers")
  public List<Manager> getJsonManager() {
    return managerRepository.findAllByOrderByUsername();
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

  @PostMapping("/addEnterprise")
  public ResponseEntity<Enterprise> addEnterprise(
      @AuthenticationPrincipal AuthorityUser userDetails,
      @RequestBody Enterprise enterpriseDetails) {
    Manager manager = userDetails.getManager();
    enterpriseDetails.setManagers(Set.of(manager));
    final Enterprise updatedEnterprise = enterpriseRepository.save(enterpriseDetails);
    return ResponseEntity.ok(updatedEnterprise);
  }

  @PostMapping("/addVehicle")
  public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicleDetails) {
    Brand brand = brandRepository.findById(vehicleDetails.getBrandId())
        .orElseThrow(() -> new EntityNotFoundException("Brand not found for this id :: "
            + vehicleDetails.getBrandId()));

    final Vehicle updatedVehicle = vehicleRepository.save(vehicleDetails);
    brand.setVehicle(updatedVehicle);
    brandRepository.save(brand);
    return ResponseEntity.ok(updatedVehicle);
  }

  @PutMapping("/updateEnterprise/{id}")
  public ResponseEntity<Enterprise> updateEnterprise(
      @AuthenticationPrincipal AuthorityUser userDetails,
      @PathVariable Long id,
      @RequestBody Enterprise enterpriseDetails) {
    Manager manager = userDetails.getManager();

    if (!manager.getEnterprises().stream().map(Enterprise::getId).toList().contains(id)) {
      throw new PermissionException("Manager with id=" + manager.getId()
          + " has no permission to update Enterprise id=" + id, HttpStatus.FORBIDDEN);
    }

    Enterprise enterprise = enterpriseRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Enterprise not found for this id :: " + id));

    enterprise.setName(enterpriseDetails.getName());
    enterprise.setCity(enterpriseDetails.getCity());

    final Enterprise updatedEnterprise = enterpriseRepository.save(enterprise);
    return ResponseEntity.ok(updatedEnterprise);
  }

  @PutMapping("/updateVehicle/{id}")
  public ResponseEntity<Vehicle> updateVehicle(
      @AuthenticationPrincipal AuthorityUser userDetails,
      @PathVariable Long id,
      @RequestBody Vehicle vehicleDetails) {
    Manager manager = userDetails.getManager();
    Vehicle vehicle = vehicleRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Vehicle not found for this id :: " + id));
    Brand brand = brandRepository.findById(vehicleDetails.getBrandId())
        .orElseThrow(() -> new EntityNotFoundException("Brand not found for this id :: "
            + vehicleDetails.getBrandId()));

    if (manager.getEnterprises().stream().map(Enterprise::getVehicles).noneMatch(list
        -> list.stream().map(Vehicle::getId).toList().contains(id))) {
      throw new PermissionException("Manager with id=" + manager.getId()
          + " has no permission to update Enterprise id=" + id, HttpStatus.FORBIDDEN);
    }

    brand.setVehicle(vehicle);
    vehicle.setYear(vehicleDetails.getYear());
    vehicle.setPrice(vehicleDetails.getPrice());
    vehicle.setMileage(vehicleDetails.getMileage());
    vehicle.setBrand(brand);

    final Vehicle updatedVehicle = vehicleRepository.save(vehicle);
    brandRepository.save(brand);
    return ResponseEntity.ok(updatedVehicle);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(EntityNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PermissionException.class)
  public ResponseEntity<String> handlePermissionException(PermissionException ex) {
    return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
  }
}
