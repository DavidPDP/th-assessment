package com.fonyou.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fonyou.entities.Employee;
import com.fonyou.entities.Employee.MonthlySalary;
import com.fonyou.entities.InvalidEntityException;
import com.fonyou.repos.EmployeeRepo;

import lombok.AllArgsConstructor;

/**
 * Endpoints for the employee collection.
 * @author Johan Ballesteros.
 * @version 1.0.0.
 */
@RestController
@RequestMapping(value = "/employees", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class EmployeeController {

	private EmployeeRepo employeeRepo;
	
	@PostMapping
	public ResponseEntity<Employee> create(@RequestBody(required = true) Employee employee) throws InvalidEntityException {
		employee.checkIsValidEmployee();
		return ResponseEntity.ok(employeeRepo.save(employee));
	}
	
	@PutMapping
	public ResponseEntity<Employee> update(@RequestBody(required = true) Employee employee) throws InvalidEntityException {
		
		employee.checkIsValidEmployee();
		
		if(employee.getId() == null) {
			throw new InvalidEntityException("The id field cannot be null.");
		}
		
		return ResponseEntity.ok(employeeRepo.save(employee));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(required = true) Integer id) {
		employeeRepo.deleteById(id);
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> retrieve(@PathVariable(required = true) Integer id) throws InvalidEntityException {
		
		Optional<Employee> searchedEmployee = employeeRepo.findById(id);
		
		if(!searchedEmployee.isPresent()) {
			throw new InvalidEntityException("There is no entity with the given parameters.");
		}
		
		return ResponseEntity.ok(employeeRepo.findById(id).get());
	}
	
	@GetMapping("/{id}/pay")
	public ResponseEntity<MonthlySalary> retrieveMonthlySalary(
			@PathVariable(required = true) Integer id, 
			@RequestParam(required = true) Integer year, 
			@RequestParam(required = true) Integer month) throws InvalidEntityException {
		
		Optional<Employee> employee = employeeRepo.findById(id);
		
		if(!employee.isPresent()) {
			throw new InvalidEntityException("There is no entity with the given parameters.");
		}
		
		return ResponseEntity.ok(employee.get().calculateSalary(year, month));
		
	}
	
}
