package com.example.test.controller;

import com.example.test.service.IAreaService;
import com.example.test.service.IParkcarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/parkcar")
public class ParkcarController {
    @Autowired
    private IParkcarService iParkcarService;

}
