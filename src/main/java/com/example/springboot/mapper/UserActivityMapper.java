package com.example.springboot.mapper;

import com.example.springboot.controller.dto.ActUserDTO;
import com.example.springboot.controller.dto.MyActDTO;
import com.example.springboot.entity.UserActivity;
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
 * @since 2023-04-23
 */
public interface UserActivityMapper extends BaseMapper<UserActivity> {

    /**
     * 根据活动id获取学生用户列表
     * @param actId
     * @return
     */
    @Select("SELECT s.username as num,s.nickname as name\n" +
            "FROM sys_user s\n" +
            "WHERE NOT EXISTS(\n" +
            "SELECT 1 FROM user_activity u\n" +
            "WHERE s.username = u.user_id and u.act_id = #{actId}\n" +
            ") AND s.role = 'ROLE_STU'")
    List<ActUserDTO> selectStuByActId(@Param("actId") Integer actId);

    /**
     * 根据活动id获取未选活动老师信息
     * @param actId
     * @return
     */
    @Select("SELECT s.username as num,s.nickname as name\n" +
            "FROM sys_user s\n" +
            "WHERE NOT EXISTS(\n" +
            "SELECT 1 FROM user_activity u\n" +
            "WHERE s.username = u.user_id and u.act_id = #{actId}\n" +
            ") AND s.role = 'ROLE_TEA'")
    List<ActUserDTO> selectTeaByActId(@Param("actId") Integer actId);

    @Select("SELECT activity.id as id,activity.`name` as name\n" +
            "FROM activity,user_activity\n" +
            "WHERE activity.id = user_activity.act_id AND user_activity.user_id = #{userId}\n")
    List<MyActDTO> selectMyActByUser(@Param("userId") String userId);
}
