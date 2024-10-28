package com.wusuowei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_menu")
public class Menu {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;            //菜单名

    private String path;           //菜单路径

    private String component;       //菜单组件

    private String icon;            //菜单icon

    private Integer orderNum;        //排序

    private Integer parentId;        //父id

    private Integer isHidden;       //是否隐藏

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}

