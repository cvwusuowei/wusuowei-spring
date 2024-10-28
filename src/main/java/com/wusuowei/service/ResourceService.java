package com.wusuowei.service;

import com.wusuowei.entity.Resource;
import com.wusuowei.model.dto.LabelOptionDTO;
import com.wusuowei.model.dto.ResourceDTO;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.ResourceVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ResourceService extends IService<Resource> {

    void importSwagger();

    void saveOrUpdateResource(ResourceVO resourceVO);

    void deleteResource(Integer resourceId);

    List<ResourceDTO> listResources(ConditionVO conditionVO);

    List<LabelOptionDTO> listResourceOption();

}
