package com.wusuowei.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wusuowei.model.dto.RoleContentDTO;
import okhttp3.HttpUrl;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wusuowei.constant.BigModelConstant.APPID;

/**
 * @Author:无所为
 * @Description: T0D0
 * @DataTime: 2023/9/16 14:52
 **/
public class GptUtil {
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }

    public static String processQuestion(String question){
        JSONObject requestJson=new JSONObject();
        JSONObject header=new JSONObject();  // header参数
        header.put("app_id",APPID);
        header.put("uid",UUID.randomUUID().toString().substring(0, 10));

        JSONObject parameter=new JSONObject(); // parameter参数
        JSONObject chat=new JSONObject();
        chat.put("domain","generalv2");
        chat.put("temperature",0.5);
        chat.put("max_tokens",4096);
        parameter.put("chat",chat);

        JSONObject payload=new JSONObject(); // payload参数
        JSONObject message=new JSONObject();
        JSONArray text=new JSONArray();

        RoleContentDTO roleContentDTO = new RoleContentDTO();
        roleContentDTO.setRole("user");
        roleContentDTO.setContent(question);
        text.add(JSON.toJSON(roleContentDTO));



        message.put("text",text);
        payload.put("message",message);

        requestJson.put("header",header);
        requestJson.put("parameter",parameter);
        requestJson.put("payload",payload);

        return requestJson.toString();
    }
}
