package com.corproject.corp.redis.controller;

import com.corproject.corp.redis.entity.Student;
import com.corproject.corp.redis.repository.StudentRepository;
import com.corproject.corp.redis.service.StudentService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class finalTestController {
    @Autowired
    StudentService studentService;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    StudentRepository studentRepository;

    @RequestMapping("/test11")
    public void saveTest(){
        Student student1 = new Student("1","김씨","1");
        Student student2 = new Student("2","이씨","1");
        Student student3 = new Student("3","박씨","1");

        studentService.save(student1);
        studentService.save(student2);
        studentService.save(student3);
    }

    @RequestMapping("/test12")
    public void test2(){
        long start1 = System.currentTimeMillis();

        System.out.println(studentService.findById("1").toString());
        System.out.println(studentService.findById("2").toString());
        System.out.println(studentService.findById("3").toString());

        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);

        long start2 = System.currentTimeMillis();

        System.out.println(studentService.findById("1").toString());
        System.out.println(studentService.findById("2").toString());
        System.out.println(studentService.findById("3").toString());

        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);

        long start3 = System.currentTimeMillis();

        System.out.println(studentService.findById("1").toString());
        System.out.println(studentService.findById("2").toString());
        System.out.println(studentService.findById("3").toString());

        long end3 = System.currentTimeMillis();
        System.out.println(end3 - start3);
    }

    @RequestMapping("/test13")
    public void test3() throws Exception {
        studentService.update("1","updated name");
        Student selectStudent = studentRepository.findById("1").get();
        System.out.println(selectStudent.toString());

        Student redisStudent = studentService.findById("1");
        System.out.println(redisStudent.toString());
        //Thread.sleep(1000);
    }

    @RequestMapping("/test14")
    public void test4() throws Exception {
        System.out.println(studentService.findById("3"));
        studentService.delete("3");
        Thread.sleep(1000);
    }

}
