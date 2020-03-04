package com.lxs.community.service.impl;

import com.lxs.community.mapper.UserInfoMapper;
import com.lxs.community.dto.ResponseVO;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.pojo.UserInfo;
import com.lxs.community.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Mr.Li
 * @date 2020/2/29 - 16:46
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public ResponseVO<UserInfo> login(String email, String password) {
        UserInfo userInfo = userInfoMapper.selectByEmail(email);
        if (userInfo == null) {
            //用户不存在 返回： 用户名或则密码错误
            return ResponseVO.errorOf(ResponseEnum.EMAIL_OR_PASSWORD_ERROR);
        }
        //忽略大小写比较
        log.info("MD5 ==== {}", DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));

        if (!userInfo.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //用户不存在 返回： 用户名或则密码错误
            return ResponseVO.errorOf(ResponseEnum.EMAIL_OR_PASSWORD_ERROR);
        }
        //将密码隐藏
        userInfo.setPassword("");
        return ResponseVO.OkOf(userInfo);
    }

    @Override
    public ResponseVO<UserInfo> register(UserInfo userInfo) {
        log.info("userInfo: {}", userInfo);
        //email不能重复
        int countByEmail = userInfoMapper.countByEmail(userInfo.getEmail());
        if (countByEmail > 0) {
            return ResponseVO.errorOf(ResponseEnum.EMAIL_EXIST);
        }

        //MD5加密
        userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes(StandardCharsets.UTF_8)));

        //生成token
        String token = UUID.randomUUID().toString();
        userInfo.setToken(token);

        //入库
        int resultCount = userInfoMapper.insertSelective(userInfo);
        if (resultCount == 0) {
            return ResponseVO.errorOf(ResponseEnum.ERROR);
        }
        //将密码隐藏
        userInfo.setPassword("");
        return ResponseVO.OkOf(userInfo);
    }
}
