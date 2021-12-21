package com.hellodu.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hellodu.seckill.entity.Order;
import com.hellodu.seckill.entity.SeckillOrder;
import com.hellodu.seckill.entity.User;
import com.hellodu.seckill.entity.vo.GoodsVo;
import com.hellodu.seckill.service.GoodsService;
import com.hellodu.seckill.service.OrderService;
import com.hellodu.seckill.service.SeckillOrderService;
import com.hellodu.seckill.service.UserService;
import com.hellodu.seckill.utils.R;
import com.hellodu.seckill.utils.ResultCode;
import com.hellodu.seckill.utils.exceptionhandler.MyExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private OrderService orderService;



    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public R doSeckill(@CookieValue(value = "userToken",required = false) String token, String goodsId) {
        User user = userService.getUserByCookie(token);
        if(user == null) return R.error().code(ResultCode.NotLoginError).message("请先登录");

        // 去数据库中拿到真实库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        // 判断库存够不够
        if(goodsVo.getStockCount() < 1) {
            return R.error().code(ResultCode.NoStockError).message("库存不足");
        }
        // 判断是否重复下单
        int i = seckillOrderService.count(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if(i > 0) {
            //throw new MyExceptionHandler(ResultCode.RepeatSeckillError, "该商品每人限购一件");
            return R.error().code(ResultCode.RepeatSeckillError).message("该商品每人限购一件");
        }
        Order order = orderService.createOrder(user, goodsVo);

        return R.ok().data("order", order);
    }


    /**
     * 秒杀
     * @param model
     * @param token
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSeckill2")
    public String doSeckill2(Model model, @CookieValue(value = "userToken",required = false) String token, String goodsId) {
        User user = userService.getUserByCookie(token);
        if(user == null) return "login";
        model.addAttribute("user", user);
        // 去数据库中拿到真实库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        // 判断库存够不够
        if(goodsVo.getStockCount() < 1) {
            throw new MyExceptionHandler(ResultCode.NoStockError, "库存不足");
        }
        // 判断是否重复下单
        int i = seckillOrderService.count(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if(i > 0) {
            throw new MyExceptionHandler(ResultCode.RepeatSeckillError, "该商品每人限购一件");
        }
        Order order = orderService.createOrder(user, goodsVo);
        model.addAttribute("order", order);
        model.addAttribute("goods", goodsVo);
        return "orderDetail";
    }
}
