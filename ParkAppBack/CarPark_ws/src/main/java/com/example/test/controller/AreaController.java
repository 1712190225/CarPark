package com.example.test.controller;

import com.example.test.entity.Area;
import com.example.test.entity.Resp;
import com.example.test.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/area")
public class AreaController {
    @Autowired
    private IAreaService iAreaService;
    @RequestMapping(value = "/updatearea",method = RequestMethod.POST)
    public Resp updatearea(@RequestParam float price, @RequestParam int totalnum){
        return iAreaService.updatearea(price,totalnum);
    }
    @RequestMapping(value = "/listarea",method = RequestMethod.POST)
    public  Resp<Area> listarea(){
        return iAreaService.listarea();
    }
}
