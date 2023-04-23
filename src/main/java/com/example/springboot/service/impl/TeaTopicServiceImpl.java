package com.example.springboot.service.impl;

import cn.hutool.log.Log;
import com.example.springboot.controller.dto.TeaTopicDTO;
import com.example.springboot.entity.TeaTopic;
import com.example.springboot.mapper.TeaTopicMapper;
import com.example.springboot.service.ITeaTopicService;
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
 * @since 2023-04-24
 */
@Service
public class TeaTopicServiceImpl extends ServiceImpl<TeaTopicMapper, TeaTopic> implements ITeaTopicService {

    private static final Log LOG = Log.get();

    @Resource
    private TeaTopicMapper teaTopicMapper;
    @Override
    public List<TeaTopicDTO> getApplyByActId(Integer actId) {
        List<TeaTopicDTO> list = teaTopicMapper.selectApplyByActId(actId);
        return list;
    }
}
