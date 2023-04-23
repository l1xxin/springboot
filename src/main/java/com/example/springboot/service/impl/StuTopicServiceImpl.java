package com.example.springboot.service.impl;

import com.example.springboot.entity.StuTopic;
import com.example.springboot.mapper.StuTopicMapper;
import com.example.springboot.service.IStuTopicService;
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
public class StuTopicServiceImpl extends ServiceImpl<StuTopicMapper, StuTopic> implements IStuTopicService {

}
