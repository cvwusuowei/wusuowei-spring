package com.wusuowei.mapper;

import com.wusuowei.entity.UserAuth;
import com.wusuowei.entity.UserInfo;
import com.wusuowei.entity.UserRole;
import com.wusuowei.enums.LoginTypeEnum;
import com.wusuowei.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @Author:无所为
 * @Description: T0D0
 * @DataTime: 2024-06-16 20:01
 **/
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    public void selectUserById(){
        System.out.println(userInfoMapper.selectById(1));
    }
    @Test
    public void addUser(){
        UserInfo userInfo = UserInfo.builder()
                        .email("admin@163.com")
                        .nickname("shameless")
                        .avatar("beijing.aliyuncs.com/aurora/avatar/94afc6c8a232b078285a3406f0400672.jpg")
                        .build();
        userInfoMapper.insert(userInfo);
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.ADMIN.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username("user@163.com")
                .password(BCrypt.hashpw("211317wt", BCrypt.gensalt()))
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userAuthMapper.insert(userAuth);
    }
}
