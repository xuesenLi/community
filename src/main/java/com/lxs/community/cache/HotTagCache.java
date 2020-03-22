package com.lxs.community.cache;

import com.lxs.community.dto.HotTagDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Mr.Li
 * @date 2019/8/14 - 16:33
 */
@Component
@Data
@Slf4j
public class HotTagCache {
    private List<HotTagDTO> hots = new ArrayList<>();

    //拿到热度最大的 Max 个的标签
    //通过定时器调用这个方法   Map<String, HotTagDTO> tags  保存了系统的所有标签
    public void updateTags(Map<String, HotTagDTO> tags) {
        int Max = 6;
        //构建优先级队列(HotTagDTO 实现 Comparable改造为) 默认从小到大 队头 最小。 队列大小为 Max
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>();
        tags.forEach((name, hots) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(hots.getPriority());
            hotTagDTO.setQuestionCountSum(hots.getQuestionCountSum());
            hotTagDTO.setViewCountSum(hots.getViewCountSum());

            if (priorityQueue.size() < Max) {
                priorityQueue.add(hotTagDTO);
            } else {
                HotTagDTO minHot = priorityQueue.peek();
                if (hotTagDTO.compareTo(minHot) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });

        List<HotTagDTO> sortedTags = new ArrayList<>();

        HotTagDTO poll = priorityQueue.poll();
        while (poll != null) {
            sortedTags.add(0, poll);
            poll = priorityQueue.poll();
        }
        hots = sortedTags;

        log.info("hots = {}", hots);
    }
}
