package com.wusuowei.service;


import com.wusuowei.entity.JobLog;
import com.wusuowei.model.dto.JobLogDTO;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.vo.JobLogSearchVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface JobLogService extends IService<JobLog> {

    PageResultDTO<JobLogDTO> listJobLogs(JobLogSearchVO jobLogSearchVO);

    void deleteJobLogs(List<Integer> ids);

    void cleanJobLogs();

    List<String> listJobLogGroups();

}
