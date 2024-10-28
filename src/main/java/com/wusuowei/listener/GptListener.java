package com.wusuowei.listener;

import com.wusuowei.model.dto.JsonParseDTO;
import com.wusuowei.model.dto.TextDTO;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @Author:无所为
 * @Description: T0D0
 * @DataTime: 2023/9/16 15:33
 **/
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GptListener extends WebSocketListener {
    public   String question ;
    public  final Gson gson = new Gson();
    private Boolean wsClosed;
    public static String answer="";; // 大模型的答案汇总

    public  String getQuestion() {
        return question;
    }

    public  void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getWsClosed() {
        return wsClosed;
    }

    public void setWsClosed(Boolean wsClosed) {
        this.wsClosed = wsClosed;
    }

    public static String getAnswer() {
        return answer;
    }

    public static void setAnswer(String answer) {
        GptListener.answer = answer;
    }


    @Override
    @SneakyThrows
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        CompletableFuture.supplyAsync(()->webSocket.send(question));
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        JsonParseDTO jsonParseDTO = gson.fromJson(text, JsonParseDTO.class);
        if(jsonParseDTO.getHeader().getCode() != 0){
            System.out.println("发生错误，错误码为：" + jsonParseDTO.getHeader().getCode());
            System.out.println("本次请求的sid为：" + jsonParseDTO.getHeader().getSid());
            webSocket.close(1000, "");
        }
        List<TextDTO> textList = jsonParseDTO.getPayload().getChoices().getText();
        answer += textList.stream().map(TextDTO::getContent).collect(Collectors.joining(""));
        if(jsonParseDTO.getHeader().getStatus() == 2){
//            System.out.println(answer);
            webSocket.close(1000, "");
            wsClosed = true;
        }
    }

    @Override
    @SneakyThrows
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        wsClosed = true;
        super.onFailure(webSocket, t, response);
        if(null != response){
            int code = response.code();
            System.out.println("onFailure code:" + code);
            System.out.println("onFailure body:" + response.body().string());
            if (101 != code) {
                System.out.println("connection failed");
            }
        }
    }


}
