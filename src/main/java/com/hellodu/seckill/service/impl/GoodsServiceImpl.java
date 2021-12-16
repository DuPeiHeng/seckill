package com.hellodu.seckill.service.impl;

import com.hellodu.seckill.entity.Goods;
import com.hellodu.seckill.entity.vo.GoodsVo;
import com.hellodu.seckill.mapper.GoodsMapper;
import com.hellodu.seckill.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dupeiheng
 * @since 2021-12-16
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 获取商品列表
     * @return
     */
    @Override
    public List<GoodsVo> getGoodsVo() {
        return goodsMapper.getGoodsVo();
    }

    /**
     * 根据商品id获取商品详情Vo
     * @param goodId
     * @return
     */
    @Override
    public GoodsVo getGoodsVoByGoodsId(String goodId) {
        return goodsMapper.getGoodsVoByGoodsId(goodId);
    }
}
