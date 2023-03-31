package com.corproject.corp.redis.service;

import com.corproject.corp.redis.entity.Student;
import com.corproject.corp.redis.repository.StudentRepository;
import org.redisson.api.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private StudentRepository studentRepository;
    private RMapCache<String, Student> studentRMapCache;

    @Autowired
    public StudentService(StudentRepository studentRepository, RMapCache<String, Student> studentRMapCache){
        this.studentRepository = studentRepository;
        this.studentRMapCache = studentRMapCache;
    }

    public Student save(Student student){
        studentRMapCache.put(student.getId(), student);
        return student;
    }

    public Student findById(String id){
        return this.studentRMapCache.get(id);
    }

    public void update(String id, String name) throws Exception{
        Student student = studentRMapCache.get(id);
        student.setName(name);
        studentRMapCache.put(id,student);
    }

    public void delete(String id){
        studentRMapCache.remove(id);
    }

}
