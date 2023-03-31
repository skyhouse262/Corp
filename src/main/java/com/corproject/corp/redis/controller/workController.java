package com.corproject.corp.redis.controller;

import com.corproject.corp.redis.entity.Student;
import com.corproject.corp.redis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class workController {

    @Autowired
    StudentRepository studentRepository;

    @Cacheable(value = "Student")
    public Student findStudentById(String id){
        return studentRepository.findById(id).orElseThrow();
    }
}
