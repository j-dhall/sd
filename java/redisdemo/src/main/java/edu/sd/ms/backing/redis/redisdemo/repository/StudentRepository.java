package edu.sd.ms.backing.redis.redisdemo.repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.sd.ms.backing.redis.redisdemo.entity.Student;

public interface StudentRepository extends ElasticsearchRepository<Student, Integer> {
	public Optional<Student> findByName(String name);
}
