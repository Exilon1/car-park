package com.company.carpark.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
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

  @Transient
  private Long brandId;

  public Long getBrandId() {
    return (brand != null) ? brand.getId() : null;
  }
}
