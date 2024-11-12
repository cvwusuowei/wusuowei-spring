package com.wusuowei.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobStatusVO {

    @Schema(description = "任务id", required = true, type = "Integer")
    private Integer id;

    @Schema(description = "任务状态", required = true, type = "Integer")
    private Integer status;
}
