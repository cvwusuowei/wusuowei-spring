package com.wusuowei.service;

import com.wusuowei.entity.Menu;
import com.wusuowei.model.dto.LabelOptionDTO;
import com.wusuowei.model.dto.MenuDTO;
import com.wusuowei.model.dto.UserMenuDTO;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.IsHiddenVO;
import com.wusuowei.model.vo.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<MenuDTO> listMenus(ConditionVO conditionVO);

    void saveOrUpdateMenu(MenuVO menuVO);

    void updateMenuIsHidden(IsHiddenVO isHiddenVO);

    void deleteMenu(Integer menuId);

    List<LabelOptionDTO> listMenuOptions();

    List<UserMenuDTO> listUserMenus();

}
