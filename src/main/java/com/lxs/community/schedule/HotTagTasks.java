package com.lxs.community.schedule;

import com.lxs.community.cache.HotTagCache;
import com.lxs.community.mapper.QuestionMapper;
import com.lxs.community.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Mr.Li
 * @date 2019/8/14 - 16:01
 *
 * spring 定时器 scheduled
 */
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000*60*60*3)
    //@Scheduled(cron = "0 0 1 * * *")   //凌晨一点去执行
    public void reportCurrentTime() {
        int offset = 0;
        int limit = 10;

        log.info("reportCurrentTime is star {}__________", new Date());

        List<Question> list = new ArrayList<>();
        Map<String, Integer> tagMap = new HashMap<>();

        while(offset == 0 || list.size() == limit){

            list = questionMapper.findByQuestionAll(offset, limit);
            for (Question question : list) {

                String[] tags = question.getTag().split(",");
                //自定义热度 排序方式
                // 该标签对应的问题数 * 5   +  问题的回复个数
                for (String tag : tags) {
                    Integer priority = tagMap.get(tag);
                    if(priority != null){
                        tagMap.put(tag, priority + 5 + question.getCommentCount());
                    }else{
                        tagMap.put(tag, 5 + question.getCommentCount());
                    }
                }

            }
            offset += limit;
        }

        /*hotTagCache.getTags().forEach(
                (k,v) -> {
            System.out.print(k);
            System.out.print(" : ");
            System.out.print(v);
            System.out.println();
        }
        );*/

        //拿到热度最大的 Max 个的标签
        hotTagCache.updateTags(tagMap);

        log.info("reportCurrentTime is end {}__________", new Date());
    }

}
