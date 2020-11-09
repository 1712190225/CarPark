package com.example.test.service.impl;

import com.example.test.dao.IParkcarauthDao;
import com.example.test.service.IParkcarauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkcarauthService implements IParkcarauthService {
    @Autowired
    private IParkcarauthDao iParkcarauthDao;


}
