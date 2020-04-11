package com.lxs.community.service.impl;

import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.dto.ResponseVO;
import com.lxs.community.dto.UserVO;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.mapper.FollowMapper;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.Follow;
import com.lxs.community.model.User;
import com.lxs.community.service.FollowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public PaginationDTO getFansList(Integer uId, Integer page, Integer size) {
        List<Follow> follows = followMapper.selectByFid(uId);
        if(follows.size() == 0)
            return new PaginationDTO();
        Set<Integer> uIdSet = follows.stream()
                .map(Follow::getUId)
                .collect(Collectors.toSet());
        List<User> users = userMapper.selectByIdSet(uIdSet);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(users2userVOsConvert(users));
        return paginationDTO;
    }

    @Override
    public PaginationDTO getFollowList(Integer uId, Integer page, Integer size) {
        List<Follow> follows = followMapper.selectByUid(uId);
        if(follows.size() == 0)
            return new PaginationDTO();
        Set<Integer> fIdSet = follows.stream()
                .map(Follow::getFId)
                .collect(Collectors.toSet());
        List<User> users = userMapper.selectByIdSet(fIdSet);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(users2userVOsConvert(users));
        return paginationDTO;
    }

    private UserVO user2userVOConvert(User user){
        if(user == null)
            return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    private List<UserVO> users2userVOsConvert(List<User> users){
        return users.stream()
                .map(this::user2userVOConvert)
                .collect(Collectors.toList());
    }
}
