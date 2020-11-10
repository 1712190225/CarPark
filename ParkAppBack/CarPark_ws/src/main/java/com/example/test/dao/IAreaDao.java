package com.example.test.dao;

import com.example.test.entity.Area;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAreaDao {
    Boolean updatearea(@Param("price") float price, @Param("totalnum") int totalnum);
    Area listarea();
}
