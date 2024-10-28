package com.wusuowei.service;

import com.wusuowei.entity.ExceptionLog;
import com.wusuowei.model.dto.ExceptionLogDTO;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.vo.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ExceptionLogService extends IService<ExceptionLog> {

    PageResultDTO<ExceptionLogDTO> listExceptionLogs(ConditionVO conditionVO);

}
