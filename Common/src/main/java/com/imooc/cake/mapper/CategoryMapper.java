package com.imooc.cake.mapper;

import com.imooc.cake.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {

    @Select("SELECT id,name,create_time createTime, update_time updateTime FROM category")
    List<Category> getCategories();

    @Insert("INSERT category(name,create_time,update_time) VALUES(#{name}, #{create_time}, #{update_time})")
    void addCategory(Category category);

    @Delete("DELETE FROM category WHERE id = #{id}")
    void deletCategory(Long id);
}
