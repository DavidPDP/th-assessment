package com.fonyou.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fonyou.entities.Employee;

/**
 * DDD Repository which manages the Employee type entities.
 * @author Johan Ballesteros.
 * @version 1.0.0.
 */
@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Integer> { }
