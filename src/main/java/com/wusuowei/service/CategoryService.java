package com.wusuowei.service;

import com.wusuowei.entity.Category;
import com.wusuowei.model.dto.CategoryAdminDTO;
import com.wusuowei.model.dto.CategoryDTO;
import com.wusuowei.model.dto.CategoryOptionDTO;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.vo.CategoryVO;
import com.wusuowei.model.vo.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<CategoryDTO> listCategories();

    PageResultDTO<CategoryAdminDTO> listCategoriesAdmin(ConditionVO conditionVO);

    List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO conditionVO);

    void deleteCategories(List<Integer> categoryIds);

    void saveOrUpdateCategory(CategoryVO categoryVO);

}
