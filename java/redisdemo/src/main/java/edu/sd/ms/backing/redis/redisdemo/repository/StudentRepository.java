package edu.sd.ms.backing.redis.redisdemo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.sd.ms.backing.redis.redisdemo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	public Optional<Student> findByName(String name);
}
