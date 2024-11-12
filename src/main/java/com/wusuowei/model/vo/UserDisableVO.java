package com.wusuowei.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户禁用状态")
public class UserDisableVO {

    @NotNull(message = "用户id不能为空")
    @Schema(name = "id", description = "用户id", required = true, type = "Integer")
    private Integer id;

    @NotNull(message = "用户禁用状态不能为空")
    @Schema(name = "isDisable", description = "用户禁用状态", required = true, type = "Integer")
    private Integer isDisable;

}
