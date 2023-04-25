package com.example.springboot.service.impl;

import com.example.springboot.controller.dto.ActStuDTO;
import com.example.springboot.entity.StuTask;
import com.example.springboot.mapper.StuTaskMapper;
import com.example.springboot.service.IStuTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lcx
 * @since 2023-04-25
 */
@Service
public class StuTaskServiceImpl extends ServiceImpl<StuTaskMapper, StuTask> implements IStuTaskService {

    @Resource
    private StuTaskMapper stuTaskMapper;

    @Override
    public List<ActStuDTO> getActStu(Integer actId) {
        List<ActStuDTO> list = stuTaskMapper.assign(actId);
        return list;
    }
}
