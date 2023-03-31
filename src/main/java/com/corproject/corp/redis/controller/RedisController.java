package com.corproject.corp.redis.controller;

import com.corproject.corp.redis.entity.Student;
import com.corproject.corp.redis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    workController workController;

    @RequestMapping("test1")
    public void test1(){
        Student student1 = new Student("1","김씨","1");
        Student student2 = new Student("2","이씨","1");
        Student student3 = new Student("3","박씨","1");

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
    }

    @RequestMapping("test2")
    public void test2(){
        //Student student1 = studentRepository.findById("1").get();
        //Student student2 = studentRepository.findById("2").get();
        //Student student3 = studentRepository.findById("3").get();

        Student student1 = workController.findStudentById("1");
        Student student2 = workController.findStudentById("2");
        Student student3 = workController.findStudentById("3");


        System.out.println(student1);
        System.out.println(student2);
        System.out.println(student3);
        //studentRepository.save(student1);
        //studentRepository.save(student2);
        //studentRepository.save(student3);
    }
}
