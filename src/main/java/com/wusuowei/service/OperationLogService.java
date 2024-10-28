package com.wusuowei.service;

import com.wusuowei.entity.OperationLog;
import com.wusuowei.model.dto.OperationLogDTO;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.vo.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OperationLogService extends IService<OperationLog> {

    PageResultDTO<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);

}
