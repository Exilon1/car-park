package com.company.carpark.shell;

import com.company.carpark.datamodel.Brand;
import com.company.carpark.datamodel.Driver;
import com.company.carpark.datamodel.Enterprise;
import com.company.carpark.datamodel.Vehicle;
import com.company.carpark.datamodel.constant.Type;
import com.company.carpark.repository.BrandRepository;
import com.company.carpark.repository.DriversRepository;
import com.company.carpark.repository.EnterpriseRepository;
import com.company.carpark.repository.VehicleRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class MyShell {

  private final int carCount = 8;

  @Autowired
  private VehicleRepository vehicleRepository;
  @Autowired
  private BrandRepository brandRepository;
  @Autowired
  private DriversRepository driversRepository;
  @Autowired
  private EnterpriseRepository enterpriseRepository;

  @ShellMethod("Количество машин на одну генерацию")
  public int count() {
    return carCount;
  }

  @ShellMethod("Сгенерировать предприятие")
  public String generate(@ShellOption Integer[] enterpriseIds) {
    List<Enterprise> enterprises = enterpriseRepository.findAllByIdIn(
        new HashSet<>(Arrays.asList(enterpriseIds)));

    for (Enterprise enterprise : enterprises) {
      List<Vehicle> vehicles = createVehicles(enterprise);
      List<Driver> drivers = createDrivers();
      List<Brand> brands = vehicles.stream().map(Vehicle::getBrand).toList();

      Driver driver = createActiveDriver();
      drivers.add(driver);
      vehicles.get(0).setActiveDriver(driver);
      vehicles.forEach(v -> v.setDrivers(new HashSet<>(drivers)));

      brandRepository.saveAll(brands);
      driversRepository.saveAll(drivers);
      vehicleRepository.saveAll(vehicles);

      enterprise.setDrivers(drivers);
      enterprise.setVehicles(vehicles);
    }

    enterpriseRepository.saveAll(enterprises);
    return enterprises.toString();
  }

  private List<Vehicle> createVehicles(Enterprise enterprise) {
    List<Vehicle> vehicles = new ArrayList<>();
    vehicles.add(Vehicle.builder()
        .price(BigDecimal.valueOf(4000000))
        .year(LocalDate.ofYearDay(2012, 1))
        .mileage(11.0)
        .enterprise(enterprise)
        .brand(Brand.builder()
            .name("Vesta")
            .type(Type.PASSENGER)
            .capacity(4)
            .tank(3f)
            .countOfSeats(4)
            .build())
        .build());

    vehicles.add(Vehicle.builder()
        .price(BigDecimal.valueOf(4000000))
        .year(LocalDate.ofYearDay(2013, 1))
        .mileage(12.0)
        .enterprise(enterprise)
        .brand(Brand.builder()
            .name("Subaru")
            .type(Type.PASSENGER)
            .capacity(4)
            .tank(3f)
            .countOfSeats(4)
            .build())
        .build());

    vehicles.add(Vehicle.builder()
        .price(BigDecimal.valueOf(4000000))
        .year(LocalDate.ofYearDay(2014, 1))
        .mileage(13.0)
        .enterprise(enterprise)
        .brand(Brand.builder()
            .name("Audi")
            .type(Type.PASSENGER)
            .capacity(4)
            .tank(3f)
            .countOfSeats(4)
            .build())
        .build());

    vehicles.add(Vehicle.builder()
        .price(BigDecimal.valueOf(4000000))
        .year(LocalDate.ofYearDay(2015, 1))
        .mileage(14.0)
        .enterprise(enterprise)
        .brand(Brand.builder()
            .name("HAVAL")
            .type(Type.PASSENGER)
            .capacity(4)
            .tank(3f)
            .countOfSeats(4)
            .build())
        .build());

    vehicles.add(Vehicle.builder()
        .price(BigDecimal.valueOf(4000000))
        .year(LocalDate.ofYearDay(2016, 1))
        .mileage(15.0)
        .enterprise(enterprise)
        .brand(Brand.builder()
            .name("Wolkswagen")
            .type(Type.PASSENGER)
            .capacity(4)
            .tank(3f)
            .countOfSeats(4)
            .build())
        .build());

    vehicles.add(Vehicle.builder()
        .price(BigDecimal.valueOf(4000000))
        .year(LocalDate.ofYearDay(2017, 1))
        .mileage(16.0)
        .enterprise(enterprise)
        .brand(Brand.builder()
            .name("Polo")
            .type(Type.PASSENGER)
            .capacity(4)
            .tank(3f)
            .countOfSeats(4)
            .build())
        .build());

    vehicles.add(Vehicle.builder()
        .price(BigDecimal.valueOf(4000000))
        .year(LocalDate.ofYearDay(2018, 1))
        .mileage(17.0)
        .enterprise(enterprise)
        .brand(Brand.builder()
            .name("BMW")
            .type(Type.PASSENGER)
            .capacity(4)
            .tank(3f)
            .countOfSeats(4)
            .build())
        .build());

    vehicles.add(Vehicle.builder()
        .price(BigDecimal.valueOf(4000000))
        .year(LocalDate.ofYearDay(2013, 1))
        .mileage(18.0)
        .enterprise(enterprise)
        .brand(Brand.builder()
            .name("GMC")
            .type(Type.PASSENGER)
            .capacity(4)
            .tank(3f)
            .countOfSeats(4)
            .build())
        .build());

    return vehicles;
  }

  private Driver createActiveDriver() {
    return Driver.builder()
        .active(true)
        .birthday(LocalDate.of(1989, 1, 1))
        .firstName("One")
        .lastName("Two")
        .build();
  }

  private List<Driver> createDrivers() {
    List<Driver> drivers = new ArrayList<>();
    drivers.add(Driver.builder()
        .active(false)
        .birthday(LocalDate.of(1990, 1, 1))
        .firstName("Three")
        .lastName("Four")
        .build());
    drivers.add(Driver.builder()
        .active(false)
        .birthday(LocalDate.of(1990, 1, 1))
        .firstName("Five")
        .lastName("Six")
        .build());
    return drivers;
  }

}
