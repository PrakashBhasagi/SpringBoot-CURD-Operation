package com.prakash.springboot.repository;

import com.prakash.springboot.entity.Employee;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long> {

    Employee findByFirstName(String firstName);

}
