package com.hellodu.seckill.controller;

import com.hellodu.seckill.entity.User;
import com.hellodu.seckill.entity.vo.GoodsVo;
import com.hellodu.seckill.service.GoodsService;
import com.hellodu.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/seckill/goods")
public class GoodsController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品列表页
     * @param model
     * @param token
     * @return
     */
    @RequestMapping("/toList")
    public String toList(Model model, @CookieValue(value = "userToken",required = false) String token) {
        User user = userService.getUserByCookie(token);
        if(user == null) return "login";
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.getGoodsVo());
        return "goodsList";
    }

    /**
     * 跳转商品详情页
     * @param goodId
     * @return
     */
    @RequestMapping("/toDetail/{goodId}")
    public String toDetail(Model model, @CookieValue(value = "userToken",required = false) String token, @PathVariable String goodId) {
        User user = userService.getUserByCookie(token);
        if(user == null) return "login";
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodId);

        Date startTime = goodsVo.getStartTime(); // 秒杀开始时间
        Date endTime = goodsVo.getEndTime(); // 秒杀结束时间
        // 当前时间
        Date nowTime = new Date();
        // 秒杀状态
        int seckillStatus = -1;
        // 秒杀倒计时 -- 秒
        int countdownSeconds = -1;
        if(nowTime.before(startTime)) {
            // 说明秒杀未开始
            seckillStatus = 0;
            // 前端显示秒杀倒计时
            countdownSeconds = (int)((startTime.getTime() - nowTime.getTime()) / 1000);
        } else if(nowTime.after(endTime)) {
            // 说明秒杀已经结束
            seckillStatus = 2;
        } else {
            seckillStatus = 1;
            // 秒杀进行中
            countdownSeconds = 0;
        }
        model.addAttribute("countdownSeconds", countdownSeconds);
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("goods", goodsVo);
        return "goodsDetail";
    }
}
