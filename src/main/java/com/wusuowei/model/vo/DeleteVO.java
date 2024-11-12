package com.wusuowei.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteVO {

    @NotNull(message = "id不能为空")
    @Schema(name = "ids", description = "要删除的id", required = true, type = "array", implementation = Integer.class)
    private List<Integer> ids;

    @NotNull(message = "状态值不能为空")
    @Schema(name = "isDelete", description = "删除状态", required = true, type = "integer")
    private Integer isDelete;
}
