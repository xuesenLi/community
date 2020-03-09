package com.lxs.community.utils;

import com.lxs.community.dto.MessageVO;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.Li
 * @date 2020/3/9 - 13:21
 */
public class TimeUtil {


    /**
     * 聊天消息排序
     * @param list
     */
    public static void sort(List<MessageVO> list){
        list.sort(Comparator.comparing(MessageVO::getTime));
    }


    /**
     * 时间转换成 字符串。
     * @param date
     * @return
     */
    public static String format(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
