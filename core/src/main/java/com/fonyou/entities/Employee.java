package com.fonyou.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.YearMonth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

/**
 * Entity (Aggregate root) that represents the basic information of an 
 * employee, his salary base and the time in the company.
 * @author Johan Ballesteros.
 * @version 1.0.0.
 */
@Entity
@Table(name="employees", schema = "payroll")
@Getter @Setter
public class Employee {
	
	// Constructors ----------------------------------
	public static Employee buildFromMandatoryFields(String firstName, 
			String lastName, LocalDate startDate, BigDecimal baseSalary) {
		
		var newEmployee  = new Employee();
		
		newEmployee.setFirstName(firstName);
		newEmployee.setLastName(lastName);
		newEmployee.setStartDate(startDate);
		newEmployee.setBaseSalary(baseSalary);
		
		return newEmployee;
		
	}
	// -----------------------------------------------
	
	// Properties ------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "first_name") 
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	/**
	 * Date of joining the company.
	 */
	@Column(name = "start_date")
	private LocalDate startDate;
	
	/**
	 * Date of departure from the company.
	 */
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@Column(name = "base_salary")
	private BigDecimal baseSalary;
	// -----------------------------------------------
	
	// Own Value Objects -----------------------------
	/**
	 * Value Object that represents the calculated information of 
	 * an employee's salary given a query date.
	 * @author Johan Ballesteros
	 * @version 1.0.0.
	 */
	@Value
	public class MonthlySalary {
		private LocalDateTime timestamp = LocalDateTime.now();
		private String employee;
		private BigDecimal salary;
		private YearMonth queryDate;
		private int daysWorked;
	}
	// -----------------------------------------------
	
	// State Methods --------------------------------- 
	private static int ACCOUNTING_DAYS = 30;
	
	public MonthlySalary calculateSalary(int year, int month) {
		
		YearMonth queryDate = YearMonth.of(year, month);
		LocalDate rightLimitDate = endDate != null ? endDate : LocalDate.now();
		
		int daysWorked = ACCOUNTING_DAYS;
		
		// First case: The date consulted is not in the range where the employee 
		// works or worked.
		if (queryDate.isBefore(YearMonth.from(startDate)) || 
				queryDate.isAfter(YearMonth.from(rightLimitDate))) {
				
			return new MonthlySalary(
				String.format("%s %s", firstName, lastName),
				BigDecimal.ZERO,
				queryDate,
				0
			);
			
		// Second case: The date consulted is in the same month and year as 
		// the start and end date.
		} else if (queryDate.equals(YearMonth.from(startDate)) && 
				queryDate.equals(YearMonth.from(rightLimitDate))) {
			
			daysWorked = Period.between(
				startDate, rightLimitDate
			).getDays() + calculateAccountingFit(rightLimitDate.lengthOfMonth());
		
		// Third case: The date consulted is in start date year-month but not end date.
		} else if (queryDate.getYear() == startDate.getYear() && 
				queryDate.getMonth() == startDate.getMonth()) {
		
			daysWorked = Period.between(
				startDate, queryDate.atEndOfMonth()
			).getDays() + calculateAccountingFit(queryDate.lengthOfMonth());
			
			
		// Fourth case: The date consulted is in right limit date (end date or today) 
		// year-month but not start date.
		} else if (queryDate.getYear() == rightLimitDate.getYear() && 
				queryDate.getMonth() == rightLimitDate.getMonth()) {
			
			daysWorked = Period.between(
				queryDate.atDay(1), rightLimitDate
			).getDays() + calculateAccountingFit(rightLimitDate.lengthOfMonth());
			
		}
		
		// Fifth case (implicit with daysWorked init): The date consulted is in 
		// a month that the employee worked full.
		
		var salary = baseSalary.divide(
			BigDecimal.valueOf(ACCOUNTING_DAYS), 2, RoundingMode.CEILING
		).multiply(
			BigDecimal.valueOf(daysWorked)
		);
		
		return new MonthlySalary(
			String.format("%s %s", firstName, lastName), 
			salary, 
			queryDate,
			daysWorked
		);		
		
	}
		
	/**
	 * It allows to calculate the adjustment days: 
	 * 1. The fit with respect to the accounting days of the month (30 days). 
	 * 2. The setting to add an additional day to count the current day as worked.
	 * @param lengthOfMonth
	 * @return adjusted number of days to add.
	 */
	private int calculateAccountingFit(int lengthOfMonth) {
		int excessDays = ACCOUNTING_DAYS - lengthOfMonth;
		return excessDays < 0 ? (1 + excessDays) : 1;
	}
	
	public void checkIsValidEmployee() throws InvalidEntityException {
		isBaseSalaryNonNegative();
		hasNonBlankFields();
		isStartDateGreaterThanEndDate();
		isStartDateGreaterThan1900();
	}
	
	private void isBaseSalaryNonNegative() throws InvalidEntityException {
		if(baseSalary.compareTo(BigDecimal.ZERO) < -1) {
			throw new InvalidEntityException("Base salary cannot be negative.");
		}
	}
	
	private void hasNonBlankFields() throws InvalidEntityException {
		if(firstName.isEmpty() || lastName.isEmpty()) {
			throw new InvalidEntityException("First name and last name cannot be blank.");
		}
	}
	
	private void isStartDateGreaterThanEndDate() throws InvalidEntityException {
		if(endDate != null && endDate.isBefore(startDate)) {
			throw new InvalidEntityException("The end date cannot be less than the start date.");
		}
	}
	
	// Static init -----------------------------------
	private static LocalDate baseLowerDate;
	
	static {
		baseLowerDate = LocalDate.of(1990, 1, 1);
	}
	// -----------------------------------------------
	
	private void isStartDateGreaterThan1900() throws InvalidEntityException {
		if(startDate.isBefore(baseLowerDate) || 
				(endDate != null && endDate.isBefore(baseLowerDate))) {
			throw new InvalidEntityException("Start date and end date cannot be below to 1900-01-01.");
		}
	}
	// -----------------------------------------------
	
}
