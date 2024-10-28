package com.wusuowei.strategy;


import com.wusuowei.model.dto.UserInfoDTO;

public interface SocialLoginStrategy {

    UserInfoDTO login(String data);

}
