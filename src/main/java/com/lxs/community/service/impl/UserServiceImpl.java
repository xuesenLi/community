package com.lxs.community.service.impl;

import com.lxs.community.dto.ResponseVO;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.exception.GlobalException;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.User;
import com.lxs.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 17:18
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbuser = userMapper.findByAccountId(user.getAccountId());
        if (dbuser == null) {
            //插入
            //user.setGmtCreate(System.currentTimeMillis());
            //user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新  下面的属性有可能会改变。。
            //dbuser.setGmtModified(System.currentTimeMillis());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());

            userMapper.update(dbuser);
        }

    }


    @Override
    public ResponseVO<User> login(String email, String password) {
        User User = userMapper.selectByEmail(email);
        if (User == null) {
            //用户不存在 返回： 用户名或则密码错误
            throw new GlobalException(ResponseEnum.EMAIL_OR_PASSWORD_ERROR.getMessage());
        }
        //忽略大小写比较
        log.info("MD5 ==== {}", DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));

        if (!User.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //用户不存在 返回： 用户名或则密码错误
            throw new GlobalException(ResponseEnum.EMAIL_OR_PASSWORD_ERROR.getMessage());
        }
        //将密码隐藏
        User.setPassword("");
        return ResponseVO.OkOf(User);
    }

    @Override
    public ResponseVO<User> register(User user) {
        log.info("user: {}", user);
        //email不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            //return ResponseVO.errorOf(ResponseEnum.EMAIL_EXIST);
            throw new GlobalException(ResponseEnum.EMAIL_EXIST.getMessage());
        }

        //MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        //生成token
        String token = UUID.randomUUID().toString();
        user.setToken(token);

        //入库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            //return ResponseVO.errorOf(ResponseEnum.ERROR);
            throw new GlobalException(ResponseEnum.ERROR.getMessage());
        }
        //将密码隐藏
        user.setPassword("");
        return ResponseVO.OkOf(user);
    }

    @Override
    public User selectByToken(String token) {
        User user = userMapper.selectByToken(token);
       //log.info("user ======={}", user.getName());
        return user;
    }

    @Override
    public ResponseVO<User> selectById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            return ResponseVO.errorOf(ResponseEnum.USER_EXIST);
        }
        return ResponseVO.OkOf(user);
    }
}
