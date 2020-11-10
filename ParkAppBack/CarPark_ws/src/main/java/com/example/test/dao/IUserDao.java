package com.example.test.dao;

import com.example.test.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
    User findlogin(@Param("loginname") String loginname);
    Boolean createuser(@Param("loginname") String loginname,@Param("password") String password);
}
