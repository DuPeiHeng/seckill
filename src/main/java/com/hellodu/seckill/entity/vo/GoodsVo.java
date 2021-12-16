package com.hellodu.seckill.entity.vo;

import com.hellodu.seckill.entity.Goods;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsVo extends Goods {

    private BigDecimal seckillPrice; // 商品秒杀价
    private Integer stockCount; // 秒杀库存数量
    private Date startTime; //秒杀开始时间
    private Date endTime; // 秒杀结束时间

    public GoodsVo() {
    }

    public GoodsVo(BigDecimal seckillPrice, Integer stockCount, Date startTime, Date endTime) {
        this.seckillPrice = seckillPrice;
        this.stockCount = stockCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "seckillPrice=" + seckillPrice +
                ", stockCount=" + stockCount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
