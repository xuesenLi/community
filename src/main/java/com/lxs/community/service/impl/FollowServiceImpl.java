package com.lxs.community.service.impl;

import com.lxs.community.dto.ResponseVO;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.mapper.FollowMapper;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.Follow;
import com.lxs.community.model.User;
import com.lxs.community.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Li
 * @date 2020/3/12 - 16:06
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private UserMapper userMapper;



    @Override
    public ResponseVO follow(Integer uid, Integer fid) {
        User user = userMapper.selectByPrimaryKey(fid);
        if(user == null){
            return ResponseVO.errorOf(ResponseEnum.USER_EXIST);
        }
        Follow follow = new Follow();
        follow.setUId(uid);
        follow.setFId(fid);

        int insert = followMapper.insertSelective(follow);
        if(insert == 0){
            return ResponseVO.errorOf(ResponseEnum.USER_FOLLOW_FAIL);
        }

        return ResponseVO.OkOf();
    }

    /**
     * 取消关注用户
     * @param uid
     * @param fid
     * @return
     */
    @Override
    public ResponseVO unFollow(Integer uid, Integer fid) {
        int delete = followMapper.deleteByUidAndFid(uid, fid);
        if(delete == 0){
            return ResponseVO.errorOf(ResponseEnum.USER_UN_FOLLOW_FAIL);
        }
        return ResponseVO.OkOf();
    }

    @Override
    public ResponseVO getFollow(Integer uId, Integer fId) {
        Follow follow = followMapper.selectByUidAndFid(uId, fId);
        Map<String, Boolean> map = new HashMap<>();

        if(follow != null){
            map.put("follow", true);
        }else{
            map.put("follow", false);
        }
        return ResponseVO.OkOf(map);
    }
}
