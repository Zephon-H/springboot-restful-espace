package com.zephon.controller;

import com.zephon.domain.Competition;
import com.zephon.domain.response.PageResult;
import com.zephon.entity.Result;
import com.zephon.entity.ResultCode;
import com.zephon.service.CompetitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
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
 * @date 2020/2/29 下午2:20
 * @Copyright ©
 */
@CrossOrigin
@RestController
@Api(value = "比赛相关接口", tags = "比赛相关接口")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;

//    @RequiresRoles(value = {"student","teacher","admin"}, logical = Logical.OR)
//    @GetMapping("/comps")
//    @ApiOperation("查询所有比赛")
//    public Result findAll() {
//        return new Result(ResultCode.SUCCESS, competitionService.findAll());
//    }

    @RequiresRoles(value = {"teacher","admin"}, logical = Logical.OR)
    @PostMapping("/comp")
    @ApiOperation("添加比赛")
    public Result save(@RequestBody Competition competition) {
        competitionService.save(competition);
        return new Result(ResultCode.SUCCESS);
    }

    @RequiresRoles(value = {"teacher","admin"}, logical = Logical.OR)
    @PutMapping("/comp/{id}")
    @ApiOperation("更新比赛信息")
    public Result update(@PathVariable("id") String id, @RequestBody Competition competition) {
        competition.setId(id);
        competitionService.update(competition);
        return new Result(ResultCode.SUCCESS);
    }

    @RequiresRoles(value = {"teacher","admin"}, logical = Logical.OR)
    @DeleteMapping("/comp/{id}")
    @ApiOperation("删除比赛信息")
    public Result delete(@PathVariable("id") String id) {
        competitionService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }

    @RequiresRoles(value = {"student","teacher","admin"}, logical = Logical.OR)
    @GetMapping("/comp/{id}")
    @ApiOperation("根据id查询比赛信息")
    public Result findById(@PathVariable("id") String id) {
        Competition c = competitionService.findById(id);
        return new Result(ResultCode.SUCCESS, c);
    }

    @RequiresRoles(value = {"student","teacher","admin"}, logical = Logical.OR)
    @GetMapping("/comps")
    @ApiOperation("根据类型、赛级、排序筛选比赛信息")
    public Result findByTypeAndLevelAndSort(Integer page,Integer size,
                                            @ApiParam(name = "type", value = "类型",example = "数学建模")  @PathParam("type") String type,
                                            @ApiParam(name = "level", value = "赛级",example = "校级") @PathParam("level") String level,
                                            @ApiParam(name = "sort", value = "排序条件",example = "enrollStart") @PathParam("sort") String sort,
                                            @ApiParam(name = "sortType", value = "升/降序",example = "asc",defaultValue = "desc")  @PathParam("sortType")String sortType) {
        if(page==null){
            page=1;
        }
        if(size==null){
            size=4;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        if (level != null) {
            int lev = 0;
            switch (level) {
                case "校级":
                    lev = 0;
                    break;
                case "市级":
                    lev = 1;
                    break;
                case "省级":
                    lev = 2;
                    break;
                case "国家级":
                    lev = 3;
                    break;
                default:
                    break;
            }
            map.put("level", lev);
        }
        map.put("sort", sort);
        if(sortType==null){
            sortType="desc";
        }
        map.put("sortType",sortType);
        Page<Competition> pageUser = competitionService.findByTypeAndLevelAndSort(page,size,map);
        PageResult<Competition> pageResult = new PageResult<>(pageUser.getTotalElements(), pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }
}
