package com.example.springboot.service.impl;

import com.example.springboot.entity.TeaTopic;
import com.example.springboot.mapper.TeaTopicMapper;
import com.example.springboot.service.ITeaTopicService;
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
public class TeaTopicServiceImpl extends ServiceImpl<TeaTopicMapper, TeaTopic> implements ITeaTopicService {

}
