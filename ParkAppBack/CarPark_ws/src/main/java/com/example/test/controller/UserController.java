package com.example.test.controller;

import com.example.test.entity.Resp;
import com.example.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Resp login(@RequestParam String loginname, @RequestParam String password){
        return iUserService.finduser(loginname,password);
    }
    @RequestMapping(value = "/createuser",method = RequestMethod.POST)
    public Resp createuser(@RequestParam String loginname,@RequestParam String password){
        return iUserService.createuser(loginname,password);
    }


}
