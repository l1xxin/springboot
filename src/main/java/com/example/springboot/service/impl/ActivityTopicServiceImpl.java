package com.example.springboot.service.impl;

import com.example.springboot.entity.ActivityTopic;
import com.example.springboot.mapper.ActivityTopicMapper;
import com.example.springboot.service.IActivityTopicService;
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
public class ActivityTopicServiceImpl extends ServiceImpl<ActivityTopicMapper, ActivityTopic> implements IActivityTopicService {

}
