package com.zephon.controller;

import com.zephon.domain.Competition;
import com.zephon.domain.Project;
import com.zephon.domain.response.PageResult;
import com.zephon.entity.Result;
import com.zephon.entity.ResultCode;
import com.zephon.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.controller
 * @date 2020/2/29 下午6:11
 * @Copyright ©
 */
@RestController
@Api(value = "项目相关接口", tags = "项目相关接口")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

//    @RequiresRoles(value = {"student","teacher","admin"}, logical = Logical.OR)
//    @GetMapping("/projects")
//    @ApiOperation("查询所有项目")
//    public Result findAll(){
//        return new Result(ResultCode.SUCCESS,projectService.findAll());
//    }

    @RequiresRoles(value = {"student","teacher","admin"}, logical = Logical.OR)
    @ApiOperation("根据id查询项目")
    @GetMapping("/project/{id}")
    public Result findById(@PathVariable("id")String id){
        return new Result(ResultCode.SUCCESS,projectService.findById(id));
    }

    @RequiresRoles(value = {"teacher","admin"}, logical = Logical.OR)
    @ApiOperation("创建项目")
    @PostMapping("/project")
    public Result save(@RequestBody Project project){
        projectService.save(project);
        return new Result(ResultCode.SUCCESS);
    }

    @RequiresRoles(value = {"teacher","admin"}, logical = Logical.OR)
    @ApiOperation("更新项目")
    @PutMapping("/project/{id}")
    public Result update(@PathVariable("id")String id, @RequestBody Project project){
        project.setId(id);
        projectService.update(project);
        return new Result(ResultCode.SUCCESS);
    }

    @RequiresRoles(value = {"teacher","admin"}, logical = Logical.OR)
    @DeleteMapping("/project/{id}")
    @ApiOperation("删除项目信息")
    public Result delete(@PathVariable("id") String id) {
        projectService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }


    @RequiresRoles(value = {"student","teacher","admin"}, logical = Logical.OR)
    @GetMapping("/projects")
    @ApiOperation("根据类型、排序筛选项目")
    public Result findByTypeAndLevelAndSort(Integer page,Integer size,
                                            @ApiParam(name = "type", value = "类型",example = "数学建模", required = true)  @PathParam("type") String type,
                                            @ApiParam(name = "sort", value = "排序条件",example = "enrollStart", required = true) @PathParam("sort") String sort,
                                            @ApiParam(name = "sortType", value = "升/降序",example = "asc",defaultValue = "desc")  @PathParam("sortType")String sortType) {
        if(page==null){
            page=1;
        }
        if(size==null){
            size=4;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("sort", sort);
        if(sortType==null){
            sortType="desc";
        }
        map.put("sortType",sortType);

        Page<Project> pageUser = projectService.findByTypeAndSort(page,size,map);
        PageResult<Project> pageResult = new PageResult<>(pageUser.getTotalElements(), pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    @RequiresRoles(value = {"student","teacher","admin"}, logical = Logical.OR)
    @ApiOperation("根据教师Id列表查询项目")
    @PostMapping("/projects")
    public Result findByTeacherIds(@RequestBody List<String> teacherIds){
        System.out.println(teacherIds);
        List<Project> list = projectService.findByTeacherIds(teacherIds);
        return new Result(ResultCode.SUCCESS,list);
    }
}
