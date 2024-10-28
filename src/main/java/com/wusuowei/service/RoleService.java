package com.wusuowei.service;

import com.wusuowei.entity.Role;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.dto.RoleDTO;
import com.wusuowei.model.dto.UserRoleDTO;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.RoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<UserRoleDTO> listUserRoles();

    PageResultDTO<RoleDTO> listRoles(ConditionVO conditionVO);

    void saveOrUpdateRole(RoleVO roleVO);

    void deleteRoles(List<Integer> ids);

}
