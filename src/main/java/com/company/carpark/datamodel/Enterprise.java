package com.company.carpark.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "enterprise_manager",
      joinColumns = @JoinColumn(name = "enterprise_id"),
      inverseJoinColumns = @JoinColumn(name = "manager_id"))
  private Set<Manager> managers = new HashSet<>();

  @Transient
  private List<Long> vehicleIds;

  @Transient
  private List<Long> driverIds;

  @Transient
  private List<String> managerUsernames;

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

  public List<String> getManagerUsernames() {
    return managers != null
        ? managers.stream()
        .map(Manager::getUsername)
        .collect(Collectors.toList())
        : Collections.emptyList();
  }
}
