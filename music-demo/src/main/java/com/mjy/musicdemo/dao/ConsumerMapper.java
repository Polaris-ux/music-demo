package com.mjy.musicdemo.dao;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.Consumer;
import com.mjy.musicdemo.entity.vo.ConsumerVo;

import java.util.List;

public interface ConsumerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Consumer record);

    int insertSelective(Consumer record);

    Consumer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Consumer record);

    int updateByPrimaryKey(Consumer record);

    Page<Consumer> getConsumersWithKey(String key);

    int batchRemoveConsumer(List<Integer> ids);

    int getConsumerNum();


    int getManNum();

    int isExists(String username);

    Consumer getConsumerByName(String username);

    List<ConsumerVo> getConsumerByIds(List<Integer> ids);
}