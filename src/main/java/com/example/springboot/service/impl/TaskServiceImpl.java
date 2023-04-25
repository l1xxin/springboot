package com.example.springboot.service.impl;

import com.example.springboot.entity.Task;
import com.example.springboot.mapper.TaskMapper;
import com.example.springboot.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lcx
 * @since 2023-04-25
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

}
