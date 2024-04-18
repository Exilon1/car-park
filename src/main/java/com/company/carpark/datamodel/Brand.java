package com.company.carpark.datamodel;

import com.company.carpark.datamodel.constant.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
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
public class Brand {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @JsonIgnore
  @OneToOne(optional = false)
  @JoinColumn(
      name = "vehicle_id",
      unique = true,
      nullable = false,
      updatable = false)
  private Vehicle vehicle;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private Type type;

  @Column(name = "count_of_seats")
  private Integer countOfSeats;

  @Column(name = "capacity")
  private Integer capacity;

  @Column(name = "tank")
  private Float tank;

  @Transient
  private Long vehicleId;

  public Long getVehicleId() {
    return (vehicle != null) ? vehicle.getId() : null;
  }
}
