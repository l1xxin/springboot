package com.example.springboot.mapper;

import com.example.springboot.controller.dto.TeaTopicDTO;
import com.example.springboot.entity.TeaTopic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lcx
 * @since 2023-04-24
 */
public interface TeaTopicMapper extends BaseMapper<TeaTopic> {
    /**
     * 根据活动id查询当前活动的拟题申请记录
     * @param actId
     * @return
     */
    @Select("SELECT tea_topic.id as id,tea_topic.name as name,tea_topic.des as des,\n" +
            "tea_topic.file as file,sys_user.nickname as nickname,tea_topic.tea_id as teaId,\n" +
            "tea_topic.`status` as status\n" +
            "FROM tea_topic,sys_user\n" +
            "WHERE tea_topic.tea_id = sys_user.username AND tea_topic.act_id = #{actId}\n" +
            "ORDER BY id\n")
    List<TeaTopicDTO> selectApplyByActId(@Param("actId")Integer actId);

}
