package com.hellodu.seckill.entity.vo;

import com.hellodu.seckill.entity.User;

/**
 * 商品详情返回对象Vo
 */
public class DetailVo {

    private User user;
    private GoodsVo goodsVo;
    private Integer seckillStatus;
    private Integer countdownSeconds;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public Integer getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(Integer seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public Integer getCountdownSeconds() {
        return countdownSeconds;
    }

    public void setCountdownSeconds(Integer countdownSeconds) {
        this.countdownSeconds = countdownSeconds;
    }
}
