package com.example.test.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IParkcarauthDao {
    void addSurvey(@Param("uid") int uid,@Param("abroad") byte abroad,@Param("illness") byte illness,@Param("healthCode") byte healthCode);
}
