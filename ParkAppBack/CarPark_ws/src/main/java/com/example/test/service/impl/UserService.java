package com.example.test.service.impl;

import com.example.test.dao.IUserDao;
import com.example.test.entity.Resp;
import com.example.test.entity.User;
import com.example.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserDao iUserDao;

    @Override
    public Resp finduser(String loginname, String password) {
       User user=iUserDao.findlogin(loginname);
       if(user==null)
           return Resp.customFail("400","用户名不存在");
       if(user.getPassword().equals(password))
           return Resp.success("登陆成功");
       else
           return Resp.customFail("400","密码错误");

    }

    @Override
    public Resp createuser(String loginname, String password) {
        if(null!=iUserDao.findlogin(loginname))
        return Resp.customFail("400","用户名已存在");
        if(iUserDao.createuser(loginname,password))
            return Resp.success("用户创建成功");
        else
            return Resp.error("插入数据库失败");
    }
}
