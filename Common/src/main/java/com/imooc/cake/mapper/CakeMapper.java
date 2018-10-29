package com.imooc.cake.mapper;

import com.imooc.cake.entity.Cake;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CakeMapper {

    @Select("SELECT * FROM cake ORDER BY create_time LIMIT #{skip}, #{size}")
    @Results({
            @Result(id=true, column = "id", property = "id"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "name", property = "name"),
            @Result(column = "level", property = "level"),
            @Result(column = "price", property = "price"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
            })
    List<Cake> getCakes(@Param("skip") Integer skip, @Param("size") Integer size);

    @Select("SELECT id,category_id categoryId,name,level,price,create_time createTime, update_time updateTime" +
            " FROM cake WHERE category_id = #{categoryId} ORDER BY create_time LIMIT #{skip}, #{size}")
    List<Cake> getCakesByCategoryId(@Param("skip")Integer skip, @Param("size")Integer size, @Param("categoryId") Long categoryId);

    @Select("SELECT count(*) FROM cake WHERE category_id = #{categoryId}")
    int countCakesByCategoryId(@Param("categoryId") Long categoryId);

    @Insert("INSERT cake(category_id,name,level,price,small_img,create_time,update_time)" +
            "VALUES(#{cake.categoryId}, #{cake.name}, #{cake.level}, #{cake.price}, #{cake.smallImg}, #{cake.createTime}, #{cake.updateTime})")
    void addCake(@Param("cake") Cake cake);

    @Select("SELECT small_img smallImg FROM cake WHERE id = #{id} FOR UPDATE")
    Cake getImg(@Param("id") Long id);
}
