package com.corproject.corp.redis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

//@RedisHash("student")
@Entity
@Data
@NoArgsConstructor
public class Student implements Serializable {

    @Id
    private String id;
    private String name;
    private String grade;

    public Student(String id, String name, String grade){
        this.id = id;
        this.name = name;
        this.grade = grade;
    }
}
