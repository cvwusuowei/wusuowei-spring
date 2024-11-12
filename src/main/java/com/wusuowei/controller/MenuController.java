package com.wusuowei.controller;

import com.wusuowei.annotation.OptLog;
import com.wusuowei.model.dto.LabelOptionDTO;
import com.wusuowei.model.dto.MenuDTO;
import com.wusuowei.model.dto.UserMenuDTO;
import com.wusuowei.model.vo.ResultVO;
import com.wusuowei.service.MenuService;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.IsHiddenVO;
import com.wusuowei.model.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.wusuowei.constant.OptTypeConstant.*;

@Tag(name = "菜单模块")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Operation(summary = "查看菜单列表")
    @GetMapping("/admin/menus")
    public ResultVO<List<MenuDTO>> listMenus(ConditionVO conditionVO) {
        return ResultVO.ok(menuService.listMenus(conditionVO));
    }

    @OptLog(optType =SAVE_OR_UPDATE)
    @Operation(summary = "新增或修改菜单")
    @PostMapping("/admin/menus")
    public ResultVO<?> saveOrUpdateMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "修改目录是否隐藏")
    @PutMapping("/admin/menus/isHidden")
    public ResultVO<?> updateMenuIsHidden(@RequestBody IsHiddenVO isHiddenVO) {
        menuService.updateMenuIsHidden(isHiddenVO);
        return ResultVO.ok();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除菜单")
    @DeleteMapping("/admin/menus/{menuId}")
    public ResultVO<?> deleteMenu(@PathVariable("menuId") Integer menuId) {
        menuService.deleteMenu(menuId);
        return ResultVO.ok();
    }

    @Operation(summary = "查看角色菜单选项")
    @GetMapping("/admin/role/menus")
    public ResultVO<List<LabelOptionDTO>> listMenuOptions() {
        return ResultVO.ok(menuService.listMenuOptions());
    }

    @Operation(summary = "查看当前用户菜单")
    @GetMapping("/admin/user/menus")
    public ResultVO<List<UserMenuDTO>> listUserMenus() {
        return ResultVO.ok(menuService.listUserMenus());
    }
}
