package com.example.test.service.impl;

import com.example.test.dao.IAreaDao;
import com.example.test.dao.IUserDao;
import com.example.test.entity.Area;
import com.example.test.entity.Resp;
import com.example.test.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaService implements IAreaService {
    @Autowired
    private IAreaDao iAreaDao;
    @Autowired
    private IUserDao iUserDao;


    @Override
    public Resp updatearea(float price, int totalnum) {
        if(iAreaDao.updatearea(price,totalnum))
            return Resp.success("场地信息修改成功");
        else
            return Resp.error("插入数据库失败");
    }

    @Override
    public Resp<Area> listarea() {
        Area area= iAreaDao.listarea();
        return Resp.success(area);
    }
}
