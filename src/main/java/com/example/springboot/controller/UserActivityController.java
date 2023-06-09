package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.net.URLEncoder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.controller.dto.AddUserActDTO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import org.springframework.web.multipart.MultipartFile;
import com.example.springboot.entity.User;
import com.example.springboot.utils.TokenUtils;

import com.example.springboot.service.IUserActivityService;
import com.example.springboot.entity.UserActivity;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lcx
 * @since 2023-04-23
 */
@RestController
@RequestMapping("/userActivity")
public class UserActivityController {

    @Resource
    private IUserActivityService userActivityService;

    private final String now = DateUtil.now();

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody UserActivity userActivity) {
        if (userActivity.getId() == null) {
            //userActivity.setTime(DateUtil.now());
            //userActivity.setUser(TokenUtils.getCurrentUser().getUsername());
        }
        userActivityService.saveOrUpdate(userActivity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userActivityService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        userActivityService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userActivityService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userActivityService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<UserActivity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
//        User currentUser = TokenUtils.getCurrentUser();
//        if (currentUser.getRole().equals("ROLE_USER")) {
//            queryWrapper.eq("user", currentUser.getUsername());
//        }
        return Result.success(userActivityService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 获取学生在当前活动的选题状态
     * @param userId
     * @param actId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/state")
    public Result findPage(@RequestParam String userId,
                           @RequestParam Integer actId,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize){
        QueryWrapper<UserActivity> queryWrapper = new QueryWrapper<>();
        User currentUser = TokenUtils.getCurrentUser();
        queryWrapper.like("act_id",actId);
        queryWrapper.like("user_id",userId);
        return Result.success(userActivityService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    /**
    * 导出接口
    */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<UserActivity> list = userActivityService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("UserActivity信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

        }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<UserActivity> list = reader.readAll(UserActivity.class);

        userActivityService.saveBatch(list);
        return Result.success();
    }

    private User getUser() {
        return TokenUtils.getCurrentUser();
    }

    /**
     * 批量添加用户活动数据
     * @param list
     * @return
     * @throws Exception
     */
    @PostMapping("/addUserAct")
    public Result addStu(@RequestBody List<AddUserActDTO> list) throws Exception {
        System.out.println(list);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<UserActivity> userAct = CollUtil.newArrayList();
        for (AddUserActDTO row : list) {
            UserActivity userActivity = new UserActivity();
//            userActivity.setId(null);
//            userActivity.setState(0);
            userActivity.setUserId(row.getUserId());
            userActivity.setActId(row.getActId());
            userAct.add(userActivity);
        }
        userActivityService.saveBatch(userAct);
        return Result.success();
    }

    /**
     * 上下属性一定要一样
     * @param actId
     * @return
     */
    @GetMapping("/findStu/{actId}")
    public Result findStuByActId(@PathVariable Integer actId){
        return Result.success(userActivityService.getUnselectedStuByActId(actId));
    }

    @GetMapping("/findTea/{actId}")
    public Result findTeaByActId(@PathVariable Integer actId){
        return Result.success(userActivityService.getUnselectedTeaByActId(actId));
    }

    @GetMapping("/findMyAct/{userId}")
    public Result findActByUser(@PathVariable String userId){
        return Result.success(userActivityService.getMyActByUser(userId));
    }

}

