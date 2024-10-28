package com.wusuowei.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:无所为
 * @Description: T0D0
 * @DataTime: 2023/9/16 14:49
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoicesDTO {
    List<TextDTO> text;
}