package com.mjy.musicdemo.service;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.Consumer;
import com.mjy.musicdemo.entity.vo.ConsumerVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-14 19:09
 */
public interface ConsumerService {

    /**
     * 添加用户
     * @param consumer
     * @return
     */
    boolean addConsumer(Consumer consumer);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    boolean deleteConsumerById(Integer id,String pic);

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    boolean batchRemoveConsumer(List<Integer> ids,List<String> paths);

    /**
     * 修改用户信息
     * @param consumer
     * @return
     */
    boolean updateConsumer(Consumer consumer);

    /**
     * 获取用户
     * @param key
     * @return
     */
    Page<Consumer> getConsumersByKey(String key);

    /**
     * 更新头像
     * @param id
     * @param pic
     * @param file
     * @return
     */
    boolean uploadAvatar(Integer id, String pic, MultipartFile file);

    /**
     * 获取总的用户数
     * @return
     */
    int getConsumersNum();

    int getConsumerOfMan();

    boolean isExistUser(String username);

    Consumer login(String username);

    List<ConsumerVo> getConsumerByIds(List<Integer> ids);

    Consumer getConsumerById(Integer userId);
}
