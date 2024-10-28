package com.wusuowei.service.impl;

import com.wusuowei.entity.JobLog;
import com.wusuowei.mapper.JobLogMapper;
import com.wusuowei.model.dto.JobLogDTO;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.vo.JobLogSearchVO;
import com.wusuowei.service.JobLogService;
import com.wusuowei.util.BeanCopyUtil;
import com.wusuowei.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

    @Autowired
    private JobLogMapper jobLogMapper;

    @SneakyThrows
    @Override
    public PageResultDTO<JobLogDTO> listJobLogs(JobLogSearchVO jobLogSearchVO) {
        LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<JobLog>()
                .orderByDesc(JobLog::getCreateTime)
                .eq(Objects.nonNull(jobLogSearchVO.getJobId()), JobLog::getJobId, jobLogSearchVO.getJobId())
                .like(StringUtils.isNotBlank(jobLogSearchVO.getJobName()), JobLog::getJobName, jobLogSearchVO.getJobName())
                .like(StringUtils.isNotBlank(jobLogSearchVO.getJobGroup()), JobLog::getJobGroup, jobLogSearchVO.getJobGroup())
                .eq(Objects.nonNull(jobLogSearchVO.getStatus()), JobLog::getStatus, jobLogSearchVO.getStatus())
                .between(Objects.nonNull(jobLogSearchVO.getStartTime()) && Objects.nonNull(jobLogSearchVO.getEndTime()),
                        JobLog::getCreateTime,
                        jobLogSearchVO.getStartTime(),
                        jobLogSearchVO.getEndTime());
        Page<JobLog> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<JobLog> jobLogPage = jobLogMapper.selectPage(page, queryWrapper);
        List<JobLogDTO> jobLogDTOs = BeanCopyUtil.copyList(jobLogPage.getRecords(), JobLogDTO.class);
        return new PageResultDTO<>(jobLogDTOs, (int)jobLogPage.getTotal());
    }

    @Override
    public void deleteJobLogs(List<Integer> ids) {
        LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<JobLog>().in(JobLog::getId, ids);
        jobLogMapper.delete(queryWrapper);
    }

    @Override
    public void cleanJobLogs() {
        jobLogMapper.delete(null);
    }

    @Override
    public List<String> listJobLogGroups() {
        return jobLogMapper.listJobLogGroups();
    }

}
