package com.lxs.community.dto;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/14 - 17:34
 *
 * 给返回的tag 热度 排序 :  大顶堆
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    //默认为小顶堆， 需要重写为大顶堆
    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
