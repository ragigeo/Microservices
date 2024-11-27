package com.george.employeeservice.controller;

import com.george.employeeservice.model.Employee;
import com.george.employeeservice.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@RefreshScope
public class EmployeeController {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeRepository repository;

    @PostMapping
    public Employee add(@RequestBody Employee employee) {
        LOGGER.info("Employee add: {}", employee);
        return repository.add(employee);
    }
    
    @GetMapping("/welcome")
    public String welcome() {
        LOGGER.info("welcome find");
        return "Wlecome page of Employee";
    }

    @GetMapping
    public List<Employee> findAll() {
        LOGGER.info("Employee find");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable("id") Long id) {
        LOGGER.info("Employee find: id={}", id);
        return repository.findById(id);
    }

    @GetMapping("/department/{departmentId}")
    public List<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId) {
        LOGGER.info("Employee find: departmentId={}", departmentId);
        return repository.findByDepartment(departmentId);
    }
    
    @Value("${common.property}")
	private String commonProperty;
	@Value("${my-property}")
	private String myProperty;
	@Value("${your-property}")
	private String yourProperty;
	@Value("${paymentPassword}")
	private String paymentPassword;

	@GetMapping("/properties")
	public Map<String, String> getProperties() {
		var properties = new HashMap<String, String>();
		properties.put("commonProperty", commonProperty);
		properties.put("myProperty", myProperty);
		properties.put("yourProperty", yourProperty);
		properties.put("paymentPassword", paymentPassword);

		return properties;
	}

}
