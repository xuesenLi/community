package com.lxs.community.schedule;

import com.lxs.community.utils.RedisConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Mr.Li
 * @date 2020/3/9 - 17:35
 */
@Component
@Slf4j
@EnableScheduling
public class RedisTasks {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //@Scheduled(cron = "0 0 1 * * *")   //凌晨一点去执行
    @Scheduled(cron = "0 0 3 * * *")
    public void ClearRedisData(){
        log.info("定时任务 >>>>>>>>>> 清空用户间的聊天记录");

        /*获取用户之间的聊天 的所有keys */
        Set<String> keys = redisTemplate.keys(RedisConst.CHAT_FROM_PREFIX + RedisConst.REDIS_MATCH_PREFIX);
        if(keys != null){
            keys.forEach(key ->{
                redisTemplate.delete(key);
                log.info("删除掉用户聊天记录key = {}", key);
            });
        }
    }

}
