package com.corproject.corp.redis.config;

import com.corproject.corp.jwt.dao.JwtRepository;
import com.corproject.corp.jwt.model.vo.User;
import com.corproject.corp.redis.entity.Student;
import com.corproject.corp.redis.repository.StudentRepository;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.MapWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

    private StudentRepository studentRepository;
    private RedissonClient redissonClient;
    private JwtRepository jwtRepository;

    public RedisConfig(StudentRepository studentRepository, RedissonClient redissonClient, JwtRepository jwtRepository){
        this.studentRepository = studentRepository;
        this.redissonClient = redissonClient;
        this.jwtRepository = jwtRepository;
    }

    @Bean
    public RMapCache<String, Student> studentRMapCache(){
        final RMapCache<String, Student> studentRMapCache = redissonClient.getMapCache("Student", MapOptions.<String, Student>defaults()
                .writer(getStudentMapWriter())
                .writeMode(MapOptions.WriteMode.WRITE_BEHIND));
        return studentRMapCache;
    }

    @Bean
    public RMapCache<String, User> userRMapCache(){
        final RMapCache<String, User> userRMapCache = redissonClient.getMapCache("User", MapOptions.<String, User>defaults()
                .writer(getUserMapWriter())
                .writeMode(MapOptions.WriteMode.WRITE_BEHIND));
        return userRMapCache;
    }

    private MapWriter<String, Student> getStudentMapWriter(){
        return new MapWriter<String, Student>() {
            @Override
            public void write(Map<String, Student> map) {
                map.forEach((k,v) -> {
                    studentRepository.save(v);
                });
            }

            @Override
            public void delete(Collection<String> keys) {
                keys.stream().forEach(key -> {
                    studentRepository.deleteById(key);
                });
            }
        };
    }

    private MapWriter<String, User> getUserMapWriter(){
        return new MapWriter<String, User>() {
            @Override
            public void write(Map<String, User> map) {
                map.forEach((k,v) ->{
                    jwtRepository.save(v);
                });
            }

            @Override
            public void delete(Collection<String> keys) {
                keys.stream().forEach(key -> {
                    jwtRepository.deleteById(key);
                });
            }
        };
    }
}
