package com.imooc.cake.service;

import com.imooc.cake.common.MyBatisUtils;
import com.imooc.cake.entity.Cake;
import com.imooc.cake.mapper.CakeMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CakeService {

    public List<Cake> getCakes(Integer page, Integer size) {
        SqlSession session = MyBatisUtils.openSqlSession();
        List<Cake> cakes = new ArrayList<>();
        try {
            CakeMapper mapper = session.getMapper(CakeMapper.class);
            cakes = mapper.getCakes((page - 1) * size, size);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return cakes;
    }

    public List<Cake> getCakesByCategoryId(Long categoryId, Integer page, Integer size) {
        SqlSession session = MyBatisUtils.openSqlSession();
        List<Cake> cakes = new ArrayList<>();
        try {
            CakeMapper mapper = session.getMapper(CakeMapper.class);
            cakes =  mapper.getCakesByCategoryId((page - 1) * size , size, categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return cakes;
    }


    public void addCake(Cake cake) {
        SqlSession session = MyBatisUtils.openSqlSession();
        cake.setCreateTime(new Date());
        cake.setUpdateTime(new Date());
        try {
            CakeMapper mapper = session.getMapper(CakeMapper.class);
            mapper.addCake(cake);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public int countCakesByCategoryId(Long categoryId) {
        SqlSession session = MyBatisUtils.openSqlSession();
        int count = 0;
        try {
            CakeMapper mapper = session.getMapper(CakeMapper.class);
            count = mapper.countCakesByCategoryId(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return count;
    }

    public Cake getCakeImg(Long id) {
        Cake cake = null;
        SqlSession session = MyBatisUtils.openSqlSession();
        try {
            CakeMapper mapper = session.getMapper(CakeMapper.class);
            cake = mapper.getImg(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return cake;
    }
}
