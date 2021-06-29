package edu.sd.ms.backing.redis.redisdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sd.ms.backing.redis.redisdemo.entity.Student;
import edu.sd.ms.backing.redis.redisdemo.service.StudentService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/students")
@Slf4j
public class StudentController {
	@Autowired
	StudentService studentService;

	//CREATE
	
	@PostMapping("/create")
	public Student createStudent(@RequestBody Student student) {
		log.debug("INSIDE StudentController.createStudent()");
		return studentService.saveStudent(student);
	}
	
	//READ
	
	@GetMapping
	public List<Student> getAllStudents() {
		log.debug("INSIDE StudentController.getAllStudents()");
		return studentService.getAllStudents();
	}
	
	//http://localhost:8080/students/id/2
	//{"id":2,"name":"Anaya","rollNumber":"2"}
	@GetMapping("/id/{id}")
	public Student getStudentById(@PathVariable int id) {
		log.debug("INSIDE StudentController.getStudentById()");
		//return new Student(4, "Ariyahi", "4");
		return studentService.getStudentById(id);
	}
	
	//http://localhost:8080/students/name/Keysha
	//{"id":1,"name":"Keysha","rollNumber":"1"}
	@GetMapping("/name/{name}")
	public Student getStudentByName(@PathVariable String name) {
		log.debug("INSIDE StudentController.getStudentByName()");
		return studentService.getStudentByName(name);
	}
	
	//UPDATE
	
	/* PUT PUT PUT http://localhost:8080/students/update
	 * {
		  "id": 1,
		  "name": "Keysha Dhall",
		  "rollNumber": "1"
		}
		
		GET http://localhost:8080/students/name/Keysha%20Dhall
	 */
	@PutMapping("/update")
	public Student updateStudent(@RequestBody Student student) {
		log.debug("INSIDE StudentController.updateStudent()");
		return studentService.saveStudent(student);
	}
	
	/* http://localhost:8080/students/delete
	 * {
		  "id": 3
		}
	 */
	//DELETE
	@DeleteMapping("/delete")
	public String deleteStudent(@RequestBody Student student) {
		log.debug("INSIDE StudentController.deleteStudent()");
		studentService.deleteStudent(student);
		return "Deleted student '" + student.getName() + "'.";
	}
}
