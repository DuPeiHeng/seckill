package com.hellodu.seckill.service;

import com.hellodu.seckill.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hellodu.seckill.entity.User;
import com.hellodu.seckill.entity.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dupeiheng
 * @since 2021-12-16
 */
public interface OrderService extends IService<Order> {

    Order createOrder(User user, GoodsVo goodsVo);
}
