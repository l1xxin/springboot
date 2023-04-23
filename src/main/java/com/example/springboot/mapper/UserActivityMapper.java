package com.example.springboot.mapper;

import com.example.springboot.controller.dto.ActUserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserActivity;
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
 * @since 2023-04-23
 */
public interface UserActivityMapper extends BaseMapper<UserActivity> {

    @Select("SELECT s.username as num,s.nickname as name\n" +
            "FROM sys_user s\n" +
            "WHERE NOT EXISTS(\n" +
            "SELECT 1 FROM user_activity u\n" +
            "WHERE s.username = u.user_id and u.act_id = #{actId}\n" +
            ") AND s.role = 'ROLE_STU'")
    List<ActUserDTO> selectByActId(@Param("actId") Integer actId);

}
