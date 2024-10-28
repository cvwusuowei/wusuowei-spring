package com.wusuowei.service;

import com.wusuowei.entity.Talk;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.dto.TalkAdminDTO;
import com.wusuowei.model.dto.TalkDTO;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.TalkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface TalkService extends IService<Talk> {

    PageResultDTO<TalkDTO> listTalks();

    TalkDTO getTalkById(Integer talkId);

    void saveOrUpdateTalk(TalkVO talkVO);

    void deleteTalks(List<Integer> talkIdList);

    PageResultDTO<TalkAdminDTO> listBackTalks(ConditionVO conditionVO);

    TalkAdminDTO getBackTalkById(Integer talkId);

}
