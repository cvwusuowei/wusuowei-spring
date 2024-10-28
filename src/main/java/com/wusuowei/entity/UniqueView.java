package com.wusuowei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author 无所为
 * 网站访问量
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_unique_view")
public class UniqueView {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer viewsCount;                //访问量

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
