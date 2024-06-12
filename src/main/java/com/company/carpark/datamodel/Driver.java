package com.company.carpark.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "birthday")
  private LocalDate birthday;

  @Column(name = "active")
  private boolean active;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "enterprise_id")
  private Enterprise enterprise;

  @JsonIgnore
  @ManyToMany(mappedBy = "drivers", fetch = FetchType.LAZY)
  private Set<Vehicle> vehicles = new HashSet<>();


  @Transient
  private Long enterpriseId;

  public Long getEnterpriseId() {
    return enterpriseId != null ? enterpriseId : ((enterprise != null) ? enterprise.getId() : null);
  }

  @Transient
  private List<Long> vehicleIds;

  public List<Long> getVehicleIds() {
    return vehicleIds != null ? vehicleIds :
        (vehicles != null
            ? vehicles.stream()
            .map(Vehicle::getId)
            .collect(Collectors.toList())
            : Collections.emptyList());
  }
}
