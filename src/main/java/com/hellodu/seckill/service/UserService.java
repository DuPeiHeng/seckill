package com.hellodu.seckill.service;

import com.hellodu.seckill.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hellodu.seckill.entity.vo.LoginVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dupeiheng
 * @since 2021-12-10
 */
public interface UserService extends IService<User> {
    // 用户注册
    boolean register(LoginVo loginVo);

    User login(LoginVo loginVo);

    User getUserByCookie(String userToken);
}
