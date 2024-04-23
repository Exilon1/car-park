package com.company.carpark.repository;

import com.company.carpark.datamodel.Enterprise;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

  List<Enterprise> findAllByOrderById();
}
