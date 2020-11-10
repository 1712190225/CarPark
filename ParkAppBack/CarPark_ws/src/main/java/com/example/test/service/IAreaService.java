package com.example.test.service;

import com.example.test.entity.Area;
import com.example.test.entity.Resp;

public interface IAreaService {
    Resp updatearea(float price, int totalnum);
    Resp<Area> listarea();
}
