package com.hellodu.seckill.controller;


import com.hellodu.seckill.entity.User;
import com.hellodu.seckill.entity.vo.OrderDetailVo;
import com.hellodu.seckill.service.OrderService;
import com.hellodu.seckill.service.UserService;
import com.hellodu.seckill.utils.R;
import com.hellodu.seckill.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dupeiheng
 * @since 2021-12-16
 */
@RestController
@RequestMapping("/seckill/order")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * 订单详情
     * @param token
     * @param orderId
     * @return
     */
    @RequestMapping("/orderDetail")
    public R orderDetail(@CookieValue(value = "userToken",required = false) String token, String orderId) {
        User user = userService.getUserByCookie(token);
        if(user == null) return R.error().code(ResultCode.NotLoginError).message("请先登录");

        OrderDetailVo orderDetailVo = orderService.getOrderDetail(orderId);
        return R.ok().data("orderDetailVo", orderDetailVo);
    }
}

