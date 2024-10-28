package com.wusuowei.service;

import com.wusuowei.mapper.UserAuthMapper;
import com.wusuowei.model.vo.PasswordVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @Author:无所为
 * @Description: T0D0
 * @DataTime: 2024-06-17 23:41
 **/

@SpringBootTest
public class UserAuth {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private RedisService redisService;
    @Test
    public void update(){


        PasswordVO passwordVO = PasswordVO.builder()
                .oldPassword("211317wt")
                .newPassword("211317")
                .build();

        com.wusuowei.entity.UserAuth userAuth = com.wusuowei.entity.UserAuth.builder()
                .id(1016)
                .password(BCrypt.hashpw(passwordVO.getNewPassword(), BCrypt.gensalt()))
                .build();

        userAuthMapper.updateById(userAuth);
    }
}

