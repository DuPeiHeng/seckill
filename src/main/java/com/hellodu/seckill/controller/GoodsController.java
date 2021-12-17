package com.hellodu.seckill.controller;

import com.hellodu.seckill.entity.User;
import com.hellodu.seckill.entity.vo.GoodsVo;
import com.hellodu.seckill.service.GoodsService;
import com.hellodu.seckill.service.UserService;
import com.hellodu.seckill.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seckill/goods")
public class GoodsController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 商品列表页
     * @param model
     * @param token
     * @return
     */
    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody // 页面缓存
    public String toList(Model model, @CookieValue(value = "userToken",required = false) String token,
                         HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUserByCookie(token);
        if(user == null) return "login";
        model.addAttribute("user", user);
        // 查看redis中是否有数据
        String html_goodList = (String) redisUtils.get("goodsList");
        if(!StringUtils.isEmpty(html_goodList)) { // redis有缓存数据
            return html_goodList;
        }
        // 如果redis中没有缓存数据，手动渲染，存入redis，再返回
        model.addAttribute("goodsList", goodsService.getGoodsVo());
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(),
                model.asMap());
        html_goodList = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if(!StringUtils.isEmpty(html_goodList)) {
            // 说明渲染成功了
            redisUtils.set("goodsList", html_goodList, 60);
        }
        return html_goodList;
    }

    /**
     * 跳转商品详情页
     * @param goodId
     * @return
     */
    @RequestMapping(value = "/toDetail/{goodId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(Model model, @CookieValue(value = "userToken",required = false) String token,
                           @PathVariable String goodId, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUserByCookie(token);
        if(user == null) return "login";
        model.addAttribute("user", user);
        // 从redis中获取html页面
        String html_goodsDetails = (String) redisUtils.get("goodsDetail:" + goodId);
        if(!StringUtils.isEmpty(html_goodsDetails)) {
            return html_goodsDetails;
        }

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

        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(),
                model.asMap());
        // process中第一个参数是模板名称goodsDetail.html
        html_goodsDetails = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if(!StringUtils.isEmpty(html_goodsDetails)) {
            // 说明渲染成功了
            redisUtils.set("goodsDetail:" + goodId, html_goodsDetails, 60);
        }
        return html_goodsDetails;
    }
}
