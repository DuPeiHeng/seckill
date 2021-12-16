package com.hellodu.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hellodu.seckill.entity.User;
import com.hellodu.seckill.entity.vo.LoginVo;
import com.hellodu.seckill.mapper.UserMapper;
import com.hellodu.seckill.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hellodu.seckill.utils.MD5;
import com.hellodu.seckill.utils.RedisUtils;
import com.hellodu.seckill.utils.ResultCode;
import com.hellodu.seckill.utils.SaltUtils;
import com.hellodu.seckill.utils.exceptionhandler.MyExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dupeiheng
 * @since 2021-12-10
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用户注册
     * @param loginVo
     * @return
     */
    @Override
    public boolean register(LoginVo loginVo) {
        // 手机号
        String mobile = loginVo.getMobile();
        if(StringUtils.isEmpty(mobile)) throw new MyExceptionHandler(ResultCode.MobileIsEmptyError, "手机号不能为空");
        // 密码
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(password)) throw new MyExceptionHandler(ResultCode.PwdIsEmptyError, "密码不能为空");

        User user = new User();
        user.setPhone(mobile);
        // 随机盐
        String salt = SaltUtils.getsalt();
        user.setSalt(salt);
        user.setPassword(MD5.md5(password + salt));
        return userMapper.insert(user) > 0;
    }

    /**
     * 用户登录
     * @param loginVo
     * @return
     */
    @Override
    public User login(LoginVo loginVo) {
        // 获取手机号和密码
        // 手机号
        String mobile = loginVo.getMobile();
        // 密码
        String password = loginVo.getPassword();

        // 查询手机号
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone", mobile);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null) throw new MyExceptionHandler(ResultCode.UsernameIsNotExist, "用户名不存在");

        // 说明用户存在
        // 验证密码
        if(!user.getPassword().equals(MD5.md5(password + user.getSalt()))) {
            throw new MyExceptionHandler(ResultCode.PwdError, "密码不正确");
        }

        // 登录次数加1
        user.setLoginCount(user.getLoginCount() + 1);
        // 上次登录时间
        user.setLastLoginTime(new Date());

        userMapper.updateById(user);

        user.setPassword(null);
        return user;
    }

    /**
     * 根据cookie获取User对象
     * @param userToken
     * @return
     */
    @Override
    public User getUserByCookie(String userToken) {
        if(StringUtils.isEmpty(userToken)) return null;
        User user = ((User) redisUtils.get("user:" + userToken));
        return user;
    }
}
