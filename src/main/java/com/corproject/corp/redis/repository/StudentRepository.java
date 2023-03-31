package com.corproject.corp.redis.repository;

import com.corproject.corp.redis.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,String> {
}
