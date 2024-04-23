package com.company.carpark.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "city")
  private String city;

  @JsonIgnore
  @OneToMany(mappedBy = "enterprise")
  private List<Vehicle> vehicles = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "enterprise")
  private List<Driver> drivers = new ArrayList<>();

  @Transient
  private List<Long> vehicleIds;

  @Transient
  private List<Long> driverIds;

  public List<Long> getVehicleIds() {
    return vehicles != null
        ? vehicles.stream()
        .map(Vehicle::getId)
        .collect(Collectors.toList())
        : Collections.emptyList();
  }

  public List<Long> getDriverIds() {
    return drivers != null
        ? drivers.stream()
        .map(Driver::getId)
        .collect(Collectors.toList())
        : Collections.emptyList();
  }
}
