package com.wusuowei.service;

import com.wusuowei.entity.UniqueView;
import com.wusuowei.model.dto.UniqueViewDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UniqueViewService extends IService<UniqueView> {

    List<UniqueViewDTO> listUniqueViews();

}
