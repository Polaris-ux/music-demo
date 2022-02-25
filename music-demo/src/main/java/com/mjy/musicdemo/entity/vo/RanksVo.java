package com.mjy.musicdemo.entity.vo;

import java.io.Serializable;

/**
 * @author
 * @description
 * @create 2022-02-19 13:58
 */
public class RanksVo implements Serializable {
    private Integer scoreSum;
    private Integer allNum;

    public Integer getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(Integer scoreSum) {
        this.scoreSum = scoreSum;
    }





    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }
}
