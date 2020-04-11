package com.lxs.community.enums;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * @author Mr.Li
 * @date 2020/3/22 - 10:28
 */
@Getter
public enum ProfileActionEnum {

    QUESTIONS("questions", "公开文章"),

    REPLIES("replies","最新回复"),

    FAN_LIST("fan-list","我的粉丝"),

    PRIVATE_ARTICLE("private-article","私有文章"),

    FOLLOW_LIST("follow-list","我关注的人"),

    ;


    private String name;

    private String desc;

    ProfileActionEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * 通过name 获取 枚举
     * @param name
     * @return
     */
    public static ProfileActionEnum getEnumByName(String name){
        if(StringUtils.isEmpty(name))
            return null;
        for(ProfileActionEnum enums : ProfileActionEnum.values()){
            if(enums.getName().equals(name)){
                return enums;
            }
        }
        return null;
    }
}
