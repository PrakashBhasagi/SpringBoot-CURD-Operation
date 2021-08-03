package com.prakash.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import com.prakash.springboot.entity.Employee;
import com.prakash.springboot.exception.ResourceNotFoundException;
import com.prakash.springboot.repository.EmployeeRepository;
import com.prakash.springboot.service.SequenceGeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	public List<Employee> allEmp() {
		return employeeRepository.findAll();

	}

	@RequestMapping("/index")
	public String showAll(Model model) {
		model.addAttribute("users", employeeRepository.findAll());
		return "index.html";
	}

	@RequestMapping("/emp")
	public String createEmp(@Valid Employee employee, Model model) {
		employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
		Employee e = employeeRepository.save(employee);
		if (e.getFirstName() != employee.getFirstName()) {
			System.out.println("Something went wrong while saving " + employee);
		}
		model.addAttribute("users", allEmp());
		return "redirect:/index";
	}

	@RequestMapping("/empUpdate")
	public String employeeUpdate(@Valid Employee employeeDetails, Model model) throws ResourceNotFoundException {
		String fname = employeeDetails.getFirstName();
		Employee employee = employeeRepository.findByFirstName(fname);
		if (employee == null) {
			System.out.println(fname + " NOT found ");
			model.addAttribute("users", allEmp());
			return "redirect:/index";
		}
		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setPhone(employeeDetails.getPhone());
		final Employee updatedEmployee = employeeRepository.save(employee);
		ResponseEntity.ok(updatedEmployee);
		model.addAttribute("users", allEmp());
		return "redirect:/index";
	}

	@RequestMapping("/empDel")
	public String deleteEmp(Employee employeeDetails, Model model) throws ResourceNotFoundException {
		String fname = employeeDetails.getFirstName();
		Employee employee = employeeRepository.findByFirstName(fname);
		if (employee == null) {
			// System.out.println(fname + " NOT found ");
			model.addAttribute("users", allEmp());
			return "redirect:/index";
		}
		employeeRepository.delete(employee);
		model.addAttribute("users", allEmp());
		return "redirect:/index";
	}
}
