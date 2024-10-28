package com.wusuowei.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:无所为
 * @Description: T0D0
 * @DataTime: 2023/9/18 19:43
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleContentDTO {
    private String role;
    private String content;
}
