package com.example.springboot.service.impl;

import cn.hutool.log.Log;
import com.example.springboot.entity.TaskView;
import com.example.springboot.mapper.TaskViewMapper;
import com.example.springboot.service.ITaskViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author lcx
 * @since 2023-05-03
 */
@Service
public class TaskViewServiceImpl extends ServiceImpl<TaskViewMapper, TaskView> implements ITaskViewService {

    private static final Log LOG = Log.get();

    @Resource
    private TaskViewMapper taskViewMapper;
    @Override
    public List<TaskView> getStuTaskByAct(Integer actId, String stuId) {
        List<TaskView> taskViews = taskViewMapper.selectStuTaskByAct(actId,stuId);
        return taskViews;
    }
}
