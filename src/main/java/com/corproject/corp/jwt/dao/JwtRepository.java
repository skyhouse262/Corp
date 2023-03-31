package com.corproject.corp.jwt.dao;

import com.corproject.corp.jwt.model.vo.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtRepository extends CrudRepository<User,String> {
    @Query(value = "select * from user where u_id = :userId", nativeQuery = true)
    User getByUserId(@Param("userId") String userId);
}
