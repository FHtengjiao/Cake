package com.imooc.cake.service;

import com.imooc.cake.common.MyBatisUtils;
import com.imooc.cake.entity.Category;
import com.imooc.cake.mapper.CategoryMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public class CategoryService {

    public List<Category> getCategories() {
        SqlSession session = MyBatisUtils.openSqlSession();
        List<Category> categories = null;
        try {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            categories = mapper.getCategories();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return categories;
    }

    public void addCategory(Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        SqlSession session = MyBatisUtils.openSqlSession();
        try {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            mapper.addCategory(category);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteCategory(Long id) {
        SqlSession session = MyBatisUtils.openSqlSession();
        try {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            mapper.deleteCategory(id);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
