package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	public  EmployeeController(EmployeeService theEmployeeService){
		employeeService=theEmployeeService;
	}
	// add mapping for "/list"

	// Mapping for "/list" - Displays a list of employees
	@GetMapping("/list")
	public String listEmployees(Model theModel) {

		//get the employees from db
		List<Employee> theEmployees=employeeService.findAll();

		// add to the spring model
		theModel.addAttribute("employees", theEmployees);

		return "employees/list-employees";
	}

	// Mapping for "/showFormForAdd" - Displays a form for adding a new employee
	@GetMapping("showFormForAdd")
	public String showFormForAdd(Model theModel){

		//create model attribute to bind form data
		Employee theEmployee= new Employee();

		theModel.addAttribute("employee", theEmployee);

		return "employees/employee-form";
	}

	// Mapping for "/showFormForUpdate" - Displays a form for updating an employee
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,Model theModel){

		//get the employee from the service
		Employee theEmployee=employeeService.findById(theId);

		//set employee in the model to populate the form
		theModel.addAttribute("employee",theEmployee);
		//send over to our form

		return "employees/employee-form";
	}

	// Mapping for "/save" - Handles form submission to save/update an employee
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){

		//save the employee
		employeeService.save(theEmployee);

		//use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}

	// Mapping for "/delete" - Handles deleting an employee
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId")int theId){

		//delete the employee
		employeeService.deleteById(theId);
		//redirect to the /employees/list
		return "redirect:/employees/list";
	}
}









