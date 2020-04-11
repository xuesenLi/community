package com.lxs.community.service;

import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.dto.ResponseVO;

/**
 * @author Mr.Li
 * @date 2020/3/12 - 16:02
 */
public interface FollowService {

    /**
     * 关注
     * @param uid
     * @param fid
     * @return
     */
    ResponseVO follow(Integer uid, Integer fid);

    /**
     * 取消关注
     * @param uid
     * @param fid
     * @return
     */
    ResponseVO unFollow(Integer uid, Integer fid);

    /**
     * 获取关注状态
     * @param uId
     * @param fId
     * @return
     */
    ResponseVO getFollow(Integer uId, Integer fId);

    /**
     * 获取粉丝信息
     * @param uId
     * @param page
     * @param size
     * @return
     */
    PaginationDTO getFansList(Integer uId, Integer page, Integer size);

    /**
     * 获取关注人列表
     * @param uId
     * @param page
     * @param size
     * @return
     */
    PaginationDTO getFollowList(Integer uId, Integer page, Integer size);

}
