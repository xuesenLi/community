package com.lxs.community.provider;

import com.alibaba.fastjson.JSON;
import com.lxs.community.dto.AccessTokenDTO;
import com.lxs.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Mr.Li
 * @date 2019/8/6 - 19:42
 */
@Component
@Slf4j
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        //okhttp
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //string  ==== access_token=ea20aea8e44df8a149d214b492b7bbe93f7e86d5&scope=user&token_type=bearer
            String token = string.split("&")[0].split("=")[1];   // 得到  ea20aea8e44df8a149d214b492b7bbe93f7e86d5
            return token;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try{
            Response response =  client.newCall(request).execute();

            String string = response.body().string();
            log.info("GET https://api.github.com/user 返回的 json : {}", string);
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;

        }catch (IOException e){}
        return null;
    }

}
