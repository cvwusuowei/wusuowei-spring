package com.wusuowei.service;

import com.wusuowei.entity.Tag;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.dto.TagAdminDTO;
import com.wusuowei.model.dto.TagDTO;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.TagVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<TagDTO> listTags();

    List<TagDTO> listTopTenTags();

    PageResultDTO<TagAdminDTO> listTagsAdmin(ConditionVO conditionVO);

    List<TagAdminDTO> listTagsAdminBySearch(ConditionVO conditionVO);

    void saveOrUpdateTag(TagVO tagVO);

    void deleteTag(List<Integer> tagIds);

}
