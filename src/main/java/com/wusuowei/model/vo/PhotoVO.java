package com.wusuowei.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "照片")
public class PhotoVO {

    @NotNull(message = "相册id不能为空")
    @Schema(name = "albumId", description = "相册id", required = true, type = "Integer")
    private Integer albumId;

    @Schema(name = "photoUrls", description = "照片列表", required = true, type = "List<String>")
    private List<String> photoUrls;

    @Schema(name = "photoIds", description = "照片id列表", required = true, type = "List<Integer>")
    private List<Integer> photoIds;
}
