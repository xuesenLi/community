package com.lxs.community.dto;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/14 - 17:34
 * <p>
 * 给返回的tag 热度 排序 :  大顶堆
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    //总问题数
    private Integer questionCountSum;

    //总浏览数
    private Integer viewCountSum;

    //实现为小顶堆
    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }



}
