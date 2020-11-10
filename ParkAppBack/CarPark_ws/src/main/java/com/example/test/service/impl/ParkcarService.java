package com.example.test.service.impl;

import com.example.test.dao.IAreaDao;
import com.example.test.dao.IParkcarDao;
import com.example.test.dao.IUserDao;
import com.example.test.service.IAreaService;
import com.example.test.service.IParkcarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkcarService implements IParkcarService {
    @Autowired
    private IParkcarDao iParkcarDao;


}
