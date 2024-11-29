package com.Imaginnovate.employee_tax_detection.repository;


import com.Imaginnovate.employee_tax_detection.model.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDetails, Long> {

}
