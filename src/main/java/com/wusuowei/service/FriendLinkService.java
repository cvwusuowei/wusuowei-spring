package com.wusuowei.service;

import com.wusuowei.entity.FriendLink;
import com.wusuowei.model.dto.FriendLinkAdminDTO;
import com.wusuowei.model.dto.FriendLinkDTO;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.FriendLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {

    List<FriendLinkDTO> listFriendLinks();

    PageResultDTO<FriendLinkAdminDTO> listFriendLinksAdmin(ConditionVO conditionVO);

    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);

}
