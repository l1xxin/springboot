package com.example.springboot.mapper;

import com.example.springboot.entity.StuTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lcx
 * @since 2023-04-25
 */
public interface StuTaskMapper extends BaseMapper<StuTask> {
    /**
     * 查看当前活动参与的学生列表
     * @param actId
     * @return
     */
    @Select("SELECT a.stu_id\n" +
            "FROM act_topic_view a\n" +
            "WHERE act_id=#{actId}")
    List<String> assign(@Param("actId") Integer actId);

}
