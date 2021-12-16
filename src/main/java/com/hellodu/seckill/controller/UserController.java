package com.hellodu.seckill.controller;


import com.hellodu.seckill.entity.User;
import com.hellodu.seckill.entity.vo.LoginVo;
import com.hellodu.seckill.service.UserService;
import com.hellodu.seckill.utils.CookieUtil;
import com.hellodu.seckill.utils.R;
import com.hellodu.seckill.utils.RedisUtils;
import com.hellodu.seckill.utils.exceptionhandler.MyExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dupeiheng
 * @since 2021-12-10
 */
@Controller
@RequestMapping("/seckill/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 跳转到登录页
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 跳转到注册页
     * @return
     */
    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("/register")
    @ResponseBody
    public R register(@Valid LoginVo loginVo) {
        boolean flag = userService.register(loginVo);
        if(flag) {
            return R.ok().message("注册成功");
        }
        return R.error().message("注册失败");
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public R doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.login(loginVo);
        // 生成Cookie使用UUID
        String token = UUID.randomUUID().toString().replace("-", "");
        // 将用户信息存入redis中
        redisUtils.set("user:" + token, user);
//        request.getSession().setAttribute(token, user); // 保存在服务器端
        CookieUtil.setCookie(request, response, "userToken", token); // 用户的token保存在浏览器端
        return R.ok();
    }
}

