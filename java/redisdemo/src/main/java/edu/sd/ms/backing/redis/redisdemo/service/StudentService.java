package edu.sd.ms.backing.redis.redisdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sd.ms.backing.redis.redisdemo.entity.Student;
import edu.sd.ms.backing.redis.redisdemo.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;
	
	//CREATE
	//UPDATE
	
	@Transactional
	@CachePut(value = "student", key = "#student.id")
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}
	
	//READ
	
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	@Transactional
	@Cacheable(value = "student", key = "#id")//NOTE: key is "#id" and not "#student.id"
	public Student getStudentById(int id) {
		Student student = studentRepository.findById(id).orElse(null); 
		return student;
	}
	
	public Student getStudentByName(String name) {
		return studentRepository.findByName(name).orElse(null);
	}
	
	//DELETE
	
	@Transactional
	@CacheEvict(value = "student", key = "#student.id")
	public void deleteStudent(Student student) {
		studentRepository.delete(student);
	}
}
