package com.hellodu.seckill.mapper;

import com.hellodu.seckill.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hellodu.seckill.entity.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dupeiheng
 * @since 2021-12-16
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> getGoodsVo();

    GoodsVo getGoodsVoByGoodsId(String goodsId);
}
