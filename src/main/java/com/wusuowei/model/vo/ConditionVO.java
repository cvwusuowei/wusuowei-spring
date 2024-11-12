package com.wusuowei.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "查询条件")
public class ConditionVO {

    @Schema(name = "current", description = "页码", type = "integer", example = "1")
    private Long current;

    @Schema(name = "size", description = "条数", type = "integer", example = "20")
    private Long size;

    @Schema(name = "keywords", description = "搜索内容", type = "string", example = "example keyword")
    private String keywords;

    @Schema(name = "categoryId", description = "分类id", type = "integer", example = "5")
    private Integer categoryId;

    @Schema(name = "tagId", description = "标签id", type = "integer", example = "10")
    private Integer tagId;

    @Schema(name = "albumId", description = "相册id", type = "integer", example = "3")
    private Integer albumId;

    @Schema(name = "loginType", description = "登录类型", type = "integer", example = "1")
    private Integer loginType;

    @Schema(name = "type", description = "类型", type = "integer", example = "2")
    private Integer type;

    @Schema(name = "status", description = "状态", type = "integer", example = "0")
    private Integer status;

    @Schema(name = "startTime", description = "开始时间", type = "string", format = "date-time")
    private LocalDateTime startTime;

    @Schema(name = "endTime", description = "结束时间", type = "string", format = "date-time")
    private LocalDateTime endTime;

    @Schema(name = "isDelete", description = "是否删除", type = "integer", example = "0")
    private Integer isDelete;

    @Schema(name = "isReview", description = "是否审核", type = "integer", example = "1")
    private Integer isReview;

    @Schema(name = "isTop", description = "是否置顶", type = "integer", example = "0")
    private Integer isTop;

    @Schema(name = "isFeatured", description = "是否推荐", type = "integer", example = "1")
    private Integer isFeatured;

}
