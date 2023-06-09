package com.example.springboot.service;

import com.example.springboot.entity.StuTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lcx
 * @since 2023-04-25
 */
public interface IStuTaskService extends IService<StuTask> {
    /**
     * 根据活动id查询当前活动的学生列表
     * @param actId
     * @return
     */
    List<String> getActStu(Integer actId);

}
