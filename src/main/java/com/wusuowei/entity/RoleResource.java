package com.wusuowei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role_resource")
public class RoleResource {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer roleId;         //角色id

    private Integer resourceId;     //权限id

}