package com.company.carpark.repository;

import com.company.carpark.datamodel.Enterprise;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

  List<Enterprise> findAllByOrderById();

  List<Enterprise> findAllByIdIn(Set<Integer> ids);
}
