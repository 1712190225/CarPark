package com.example.parkapp.model;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.blankj.utilcode.util.Utils;

/**
 */
//Room主要分为三个部分，分别是 Database(数据库) 、Entity(实体) 、DAO(数据访问对象)
//数据库指的就是一个数据库对象，它继承于 RoomDataBase 这个类，并且需要用 @DataBase 注解
@Database(entities = {ParkCar.class,ParkCarAuth.class}, version = 1)//两张表
public abstract class AppDatabase extends RoomDatabase {
    public abstract ParkCarDao parkCarDao();
    public abstract ParkCarAuthDao parkCarAuthDao();


    private static AppDatabase javaDatabase;

    public static AppDatabase instance(){
        if(javaDatabase==null){
            synchronized (AppDatabase.class){
                if(javaDatabase==null){
                    javaDatabase= Room.databaseBuilder(Utils.getApp(),AppDatabase.class,"doc").
                            addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    System.out.println("onCreate==========="+db.getVersion()+"==="+db.getPath());
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    System.out.println("onOpen==========="+db.getVersion()+"==="+db.getPath());
                                }
                            })
                            .allowMainThreadQueries()//允许在主线程查询数据
                            .fallbackToDestructiveMigration()//迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                            .build();
                }
            }
        }
        return javaDatabase;
    }
}
