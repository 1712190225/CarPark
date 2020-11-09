package com.example.test.controller;

import com.example.test.service.IParkcarauthService;
import com.example.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/parkcarauth")
public class ParkcarauthController {
    @Autowired
    private IParkcarauthService iParkcarauthService;
    @Autowired
    private IUserService iUserService;

}
