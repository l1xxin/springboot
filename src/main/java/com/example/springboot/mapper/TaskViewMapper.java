package com.example.springboot.mapper;

import com.example.springboot.entity.TaskView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author lcx
 * @since 2023-04-25
 */
public interface TaskViewMapper extends BaseMapper<TaskView> {
    /**
     * 学生查看活动要做的任务
     * @param actId
     * @param stuId
     * @return
     */
    @Select("SELECT *\n" +
            "FROM task_view\n" +
            "WHERE act_id = #{actId} and stu_id = #{stuId}")
    List<TaskView> selectStuTaskByAct(@Param("actId")Integer actId, @Param("stuId")String stuId);

}
