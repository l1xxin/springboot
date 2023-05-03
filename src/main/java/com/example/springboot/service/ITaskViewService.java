package com.example.springboot.service;

import com.example.springboot.entity.TaskView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author lcx
 * @since 2023-05-03
 */
public interface ITaskViewService extends IService<TaskView> {
    /**
     * 学生根据活动查看任务
     * @param actId
     * @param stuId
     * @return
     */
    List<TaskView> getStuTaskByAct(Integer actId, String stuId);
}
