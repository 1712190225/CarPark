package com.example.test.service;

import com.example.test.entity.Resp;

import java.util.List;

public interface IUserService {
    Resp finduser(String loginname, String password);
    Resp createuser(String loginname,String password);
}
