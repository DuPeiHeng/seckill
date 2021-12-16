package com.hellodu.seckill.service;

import com.hellodu.seckill.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hellodu.seckill.entity.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dupeiheng
 * @since 2021-12-16
 */
public interface GoodsService extends IService<Goods> {

    List<GoodsVo> getGoodsVo();

    GoodsVo getGoodsVoByGoodsId(String goodId);
}
