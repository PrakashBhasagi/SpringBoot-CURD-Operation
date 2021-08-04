# Code

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
    <ol>
        <li><a href="#pom.xml">POM</a></li>
        <li><a href="#Application-properties">Application properties</a></li>
    <li>
      <a href="#application">Application</a>
    </li>
    <li>
      <a href="#entity">Entity</a>
    </li>
    <li><a href="#repository">Repository</a></li>
    <li><a href="#controller">Controller</a>
    <ul>
        <li><a href="#employee controller">Employee Controller</a></li>
        <li><a href="#employee api controller">Employee API Controller</a></li>
      </ul>
  </li>
    </ol>
  
</details>
<details close="close"">
  <summary>pom.xml</summary>

# `pom.xml`

## Database Dependency

- `JDBC`

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>
```

or

- `H2`

```xml
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
```

or

- `MongoDB`

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

or

- `Postgresql`

```xml
<dependency>
	<groupId>org.postgresql</groupId>
	<artifactId>postgresql</artifactId>
	<scope>runtime</scope>
</dependency>
```



## `Spring Boot Starter web`

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
</details>

<details close="close"">
  <summary>Application.properties</summary>

# `Application.properties`

```properties
server.port=8081

spring.profiles.active=mongodbprofile

spring.thymeleaf.cache=false
spring.thymeleaf.enabled=true
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

#---
spring.config.activate.on-profile=sqlprofile
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/db
spring.datasource.username=root
spring.datasource.password=root
#---
spring.config.activate.on-profile=h2profile
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:db
spring.datasource.username=root
spring.datasource.password=root
#---
spring.config.activate.on-profile=mongodbprofile
spring.data.mongodb.uri=mongodb://localhost:27017/EmployeeDB
```

</details>

<details close="close"">
  <summary>Application</summary>

# `Application`

```java
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

</details>

<details close="close"">
  <summary>Entity</summary>

# `Entity`

```java
@Document(collection = "Employee")
public class Employee {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private long id;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String firstName;

	private String lastName;

	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	private String emailId;

	private String phone;

    //No argument constructer
    //All argument constructer
    //getters
    //setters
    }
```










</details>

<details close="close"">
  <summary>Repository</summary>
                       
# `Repository`

```java
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long> {
}
```
</details>

<details close="close"">
  <summary>Controller</summary>
  
# `Controller`

## `Employee API Controller`

- `Class`

```java
@RestController
@RequestMapping("/api/v1")
public class EmployeeApiController {
}
```

- `Get All employees`

```java
@GetMapping("/employees")
public List<Employee> getAllEmployees() {
	return employeeRepository.findAll();
}
```

- `Get Employee by ID`

```java
@GetMapping("/employees/{id}")
public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
		throws ResourceNotFoundException {
	Employee employee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
	return ResponseEntity.ok().body(employee);
}
```

- `Add Employee details `

```java
@PostMapping("/employees")
public Employee createEmployee(@Valid @RequestBody Employee employee) {
	employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
	return employeeRepository.save(employee);
}
```

- `Update Employee Details by ID`

```java
@PutMapping("/employees/{id}")
public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
		@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
	Employee employee = employeeRepository.findById(employeeId)
		.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

	employee.setEmailId(employeeDetails.getEmailId());
	employee.setLastName(employeeDetails.getLastName());
	employee.setFirstName(employeeDetails.getFirstName());
	employee.setPhone(employeeDetails.getPhone());
	final Employee updatedEmployee = employeeRepository.save(employee);
	return ResponseEntity.ok(updatedEmployee);
}
```

- `Delete Employee details by ID`

```java
@DeleteMapping("/employees/{id}")
public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
		throws ResourceNotFoundException {
	Employee employee = employeeRepository.findById(employeeId)
		.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

	employeeRepository.delete(employee);
	Map<String, Boolean> response = new HashMap<>();
	response.put("deleted", Boolean.TRUE);
	return response;
	}
```

## `Employee Controller`

- `Display all Employees Details`

```java
@RequestMapping("/index")
public String showAll(Model model) {
	model.addAttribute("users", employeeRepository.findAll());
	return "index.html";
}
```

- `Add Employee details`

```java
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
```

- `Update Employees details`

```java
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
```

- `Delete employee details`

```java
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
```
</details>
