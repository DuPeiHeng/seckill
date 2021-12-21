package com.hellodu.seckill.entity.vo;

import com.hellodu.seckill.entity.Order;

/**
 * 订单详情返回对象
 */
public class OrderDetailVo {

    private Order order;
    private GoodsVo goodsVo;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }
}
