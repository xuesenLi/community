package com.lxs.community.dto;

import com.lxs.community.exception.CustomizeErrorCode;
import com.lxs.community.exception.CustomizeException;
import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 16:58
 */
@Data
public class ResultDTO {

    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDTO OkOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }


}
