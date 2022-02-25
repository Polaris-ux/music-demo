package com.mjy.musicdemo.service.impl;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.dao.ConsumerMapper;
import com.mjy.musicdemo.entity.Consumer;
import com.mjy.musicdemo.entity.vo.ConsumerVo;
import com.mjy.musicdemo.service.ConsumerService;
import com.mjy.musicdemo.util.Consts;
import com.mjy.musicdemo.util.FileUtil;
import com.mjy.musicdemo.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-14 19:16
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;
    @Override
    public boolean addConsumer(Consumer consumer) {
        return consumerMapper.insert(consumer)>0;
    }

    @Override
    public boolean deleteConsumerById(Integer id,String pic) {
        if(pic!=null){
            if(!Consts.DEFAULT_CONSUMER_PIC.equals(pic)){
                String oldFileName=pic.substring(pic.lastIndexOf("/")+1);
                String delPath=Consts.UPLOAD_CONSUMER_AVATAR_ADDRESS+oldFileName;
                FileUtil.removeFile(delPath);
            }
        }
        return consumerMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean batchRemoveConsumer(List<Integer> ids,List<String> paths) {
        for (String path : paths) {
            if(path!=null){
                if(!Consts.DEFAULT_CONSUMER_PIC.equals(path)){
                    String oldFileName=path.substring(path.lastIndexOf("/")+1);
                    String delPath=Consts.UPLOAD_CONSUMER_AVATAR_ADDRESS+oldFileName;
                    FileUtil.removeFile(delPath);
                }
            }
        }
        return consumerMapper.batchRemoveConsumer(ids)>0;
    }

    @Override
    public boolean updateConsumer(Consumer consumer) {
        return consumerMapper.updateByPrimaryKeySelective(consumer)>0;
    }

    @Override
    public Page<Consumer> getConsumersByKey(String key) {
        return consumerMapper.getConsumersWithKey(key);
    }

    @Override
    public boolean uploadAvatar(Integer id, String pic, MultipartFile file) {
        if(pic!=null){
            if(!Consts.DEFAULT_CONSUMER_PIC.equals(pic)){
                String oldFileName=pic.substring(pic.lastIndexOf("/")+1);
                String delPath=Consts.UPLOAD_CONSUMER_AVATAR_ADDRESS+oldFileName;
                FileUtil.removeFile(delPath);
            }
        }
        String filename= IdWorker.getId()+"-"+file.getOriginalFilename();
        String savePath=Consts.UPLOAD_CONSUMER_AVATAR_ADDRESS+filename;
        File file1 = new File(savePath);
        try {
            file.transferTo(file1);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator("/img/consumerPic/"+filename);
            consumer.setUpdateTime(new Date());
            return consumerMapper.updateByPrimaryKeySelective(consumer)>0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getConsumersNum() {
        return consumerMapper.getConsumerNum();
    }

    @Override
    public int getConsumerOfMan() {
        return consumerMapper.getManNum();
    }

    @Override
    public boolean isExistUser(String username) {
        return consumerMapper.isExists(username)>0;
    }

    @Override
    public Consumer login(String username) {
        return consumerMapper.getConsumerByName(username);
    }

    @Override
    public List<ConsumerVo> getConsumerByIds(List<Integer> ids) {
        return consumerMapper.getConsumerByIds(ids);
    }

    @Override
    public Consumer getConsumerById(Integer userId) {
        return consumerMapper.selectByPrimaryKey(userId);
    }
}
