package com.example.springboot.service.impl;

import com.example.springboot.entity.StuTask;
import com.example.springboot.mapper.StuTaskMapper;
import com.example.springboot.service.IStuTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lcx
 * @since 2023-04-23
 */
@Service
public class StuTaskServiceImpl extends ServiceImpl<StuTaskMapper, StuTask> implements IStuTaskService {

}
