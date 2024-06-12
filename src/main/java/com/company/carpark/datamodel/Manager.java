package com.company.carpark.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
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
public class Manager {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  @JsonIgnore
  @Column
  private String role;

  @JsonIgnore
  @ManyToMany(mappedBy = "managers", fetch = FetchType.EAGER)
  private Set<Enterprise> enterprises = new HashSet<>();

  @Transient
  private List<Long> enterpriseIds;

  public List<Long> getEnterpriseIds() {
    return enterpriseIds != null ? enterpriseIds :
        (enterprises != null
            ? enterprises.stream()
            .map(Enterprise::getId)
            .collect(Collectors.toList())
            : Collections.emptyList());
  }
}
