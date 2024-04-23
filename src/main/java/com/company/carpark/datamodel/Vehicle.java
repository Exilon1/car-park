package com.company.carpark.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "release_date")
  private LocalDate year;

  @Column(name = "mileage")
  private Double mileage;

  @JsonIgnore
  @OneToOne(mappedBy = "vehicle",
      cascade = CascadeType.REMOVE,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private Brand brand;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "vehicle_driver",
      joinColumns = @JoinColumn(name = "vehicle_id"),
      inverseJoinColumns = @JoinColumn(name = "driver_id"))
  private Set<Driver> drivers = new HashSet<>();

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "active_driver_id")
  private Driver activeDriver;

  @Transient
  private Long brandId;

  @Transient
  private Long enterpriseId;

  public Long getEnterpriseId() {
    return (enterprise != null) ? enterprise.getId() : null;
  }

  public Long getBrandId() {
    return (brand != null) ? brand.getId() : null;
  }

  public void setActiveDriver(Driver driver) {
    if (driver == null) {
      return;
    }

    if (activeDriver != null) {
      activeDriver.setActive(false);
    }

    driver.setActive(true);
    this.activeDriver = driver;
  }
}
