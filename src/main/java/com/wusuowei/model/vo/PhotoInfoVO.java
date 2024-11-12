package com.wusuowei.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "照片信息")
public class PhotoInfoVO {

    @NotNull(message = "照片id不能为空")
    @Schema(name = "id", description = "照片id", required = true, type = "Integer")
    private Integer id;

    @NotBlank(message = "照片名不能为空")
    @Schema(name = "photoName", description = "照片名", required = true, type = "String")
    private String photoName;

    @Schema(name = "photoDesc", description = "照片描述", type = "String")
    private String photoDesc;
}
