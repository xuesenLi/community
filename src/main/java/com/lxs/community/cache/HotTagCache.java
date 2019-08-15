package com.lxs.community.cache;

import com.lxs.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Mr.Li
 * @date 2019/8/14 - 16:33
 */
@Component
@Data
public class HotTagCache {
    private List<String> hots = new ArrayList<>();

    //总问题数
    private Integer questionCountSum;

    //总浏览数
    private Integer viewCountSum;

    //拿到热度最大的 Max 个的标签
    //通过定时器调用这个方法
    public void updateTags(Map<String,Integer> tags){
        int Max = 6;
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>();
        tags.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);

            if(priorityQueue.size() < Max){
                priorityQueue.add(hotTagDTO);
            }else{
                HotTagDTO minHot = priorityQueue.peek();
                if(hotTagDTO.compareTo(minHot) > 0 ){
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });

        List<String> sortedTags = new ArrayList<>();

        HotTagDTO poll = priorityQueue.poll();
        while (poll != null){
            sortedTags.add(0,poll.getName());
            poll = priorityQueue.poll();
        }
        hots = sortedTags;
        System.out.println(hots);

    }
}
