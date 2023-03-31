package com.corproject.corp.jwt.service;

import com.corproject.corp.jwt.dao.JwtRepository;
import com.corproject.corp.jwt.model.vo.User;
import com.corproject.corp.jwt.model.vo.UserDto;
import com.corproject.corp.redis.repository.StudentRepository;
import org.checkerframework.checker.units.qual.A;
import org.redisson.api.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private JwtRepository jwtRepository;
    private RMapCache<String,User> userRMapCache;

    @Autowired
    public UserService(JwtRepository jwtRepository, RMapCache<String,User> userRMapCache){
        this.jwtRepository = jwtRepository;
        this.userRMapCache = userRMapCache;
    }

    public User save(User user){
        userRMapCache.put(Integer.toString(user.getUid()), user);
        return user;
    }

    public User findById(String id) { return this.userRMapCache.get(id); }

    public void update(String id, String name) throws Exception{
        User user = userRMapCache.get(id);
        user.setName(name);
        userRMapCache.put(id,user);
    }

    public void delete(String id) { userRMapCache.remove(id); }

    public int userJoin(User user) {
        User check = new User();
        try{
            check = findById(user.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        if(check == null){
            userRMapCache.put(user.getId(),user);
        }else{
            return 1;
        }
        return 0;
    }

    public Optional<UserDto> login(UserDto userVO){
        return null;
    }
    public User loginUser(User umodel) {
        User user = userRMapCache.get(umodel.getId());
        if(user == null){
            user = jwtRepository.getByUserId(umodel.getId());
            if(user != null){//redis에는 없는 경우 저장하기
                userRMapCache.put(user.getId(),user);
            }
        }
        return user;
    }

    public ArrayList<User> getAllUser() {
        Collection<User> list = userRMapCache.values();
        ArrayList<User> result = new ArrayList<>(list);
        return result;
    }
}
