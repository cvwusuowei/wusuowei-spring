package com.wusuowei.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:无所为
 * @Description: T0D0
 * @DataTime: 2023/9/16 14:49
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextDTO {
    String role;
    String content;
}
