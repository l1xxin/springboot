package com.example.springboot.service.impl;

import com.example.springboot.entity.TaskView;
import com.example.springboot.mapper.TaskViewMapper;
import com.example.springboot.service.ITaskViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author lcx
 * @since 2023-04-25
 */
@Service
public class TaskViewServiceImpl extends ServiceImpl<TaskViewMapper, TaskView> implements ITaskViewService {

}
