package com.lxs.community.schedule;

import com.lxs.community.cache.HotTagCache;
import com.lxs.community.dto.HotTagDTO;
import com.lxs.community.mapper.QuestionMapper;
import com.lxs.community.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Mr.Li
 * @date 2019/8/14 - 16:01
 * <p>
 * spring 定时器 scheduled
 */
@Component
@Slf4j
@EnableScheduling
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)  // 启动项目默认执行一次， 然后每3 个小时执行一次，
    //@Scheduled(cron = "0 0 1 * * *")   //凌晨一点去执行
    public void reportCurrentTime() {
        int offset = 0;
        int limit = 10;

        log.info("reportCurrentTime is star {}__________", new Date());

        List<Question> list = new ArrayList<>();
        Map<String, HotTagDTO> tagMap = new HashMap<>();

        //拿到所有的问题， 取出tag, 计算热度。
        while (offset == 0 || list.size() == limit) {

            list = questionMapper.findByQuestionAll(offset, limit);
            for (Question question : list) {

                String[] tags = question.getTag().split(",");
                //自定义热度 排序方式
                // 该标签对应的问题数 * 5   +  问题的回复个数
                for (String tag : tags) {
                    HotTagDTO hotTagDTO = tagMap.get(tag);
                    if (hotTagDTO != null) {
                        HotTagDTO hotTagDTO1 = new HotTagDTO();

                        hotTagDTO1.setPriority(hotTagDTO.getPriority() + 5 + question.getCommentCount());
                        hotTagDTO1.setQuestionCountSum(hotTagDTO.getQuestionCountSum() + 1);
                        hotTagDTO1.setViewCountSum(hotTagDTO.getViewCountSum() + question.getViewCount());

                        tagMap.put(tag, hotTagDTO1);
                    } else {

                        HotTagDTO hotTagDTO1 = new HotTagDTO();

                        hotTagDTO1.setPriority(5 + question.getCommentCount());
                        hotTagDTO1.setQuestionCountSum(1);
                        hotTagDTO1.setViewCountSum(question.getViewCount());

                        tagMap.put(tag, hotTagDTO1);
                    }
                }

            }
            offset += limit;
        }

        //拿到热度最大的 Max 个的标签
        hotTagCache.updateTags(tagMap);


        log.info("reportCurrentTime is end {}__________", new Date());
    }

}
