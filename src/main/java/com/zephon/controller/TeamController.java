package com.zephon.controller;

import com.zephon.domain.Project;
import com.zephon.domain.Team;
import com.zephon.domain.User;
import com.zephon.domain.request.TeamInfo;
import com.zephon.domain.response.PageResult;
import com.zephon.entity.Result;
import com.zephon.entity.ResultCode;
import com.zephon.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.interfaces.PBEKey;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.controller
 * @date 2020/2/29 下午7:34
 * @Copyright ©
 */
@CrossOrigin
@RestController
@Api(value = "队伍相关接口", tags = "队伍相关接口")
public class TeamController {
    @Autowired
    private TeamService teamService;


    @RequiresRoles(value = {"student","teacher","admin"}, logical = Logical.OR)
    @GetMapping("/team/{id}")
    @ApiOperation("根据id查询队伍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "队伍id", dataType = "String",required = true,paramType = "path"),
    })
    public Result findById(@PathVariable("id")String id){
        Team team = teamService.findById(id);
        return new Result(ResultCode.SUCCESS,team);
    }

    @RequiresRoles(value = {"student","admin"}, logical = Logical.OR)
    @PostMapping("/team")
    @ApiOperation("保存队伍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TeamInfo", value = "队伍信息", dataType = "TeamInfo",required = true),
    })
    public Result save(@RequestBody TeamInfo teamInfo){
        teamService.save(teamInfo);
        return new Result(ResultCode.SUCCESS);
    }

    @RequiresRoles(value = {"student","admin"}, logical = Logical.OR)
    @DeleteMapping("/team/{id}")
    @ApiOperation("根据Id队伍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "队伍id", dataType = "String",required = true,paramType = "path"),
    })
    public Result delete(@PathVariable String id){
        teamService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }

    @RequiresRoles(value = {"student","admin"}, logical = Logical.OR)
    @PutMapping("/team/{id}")
    @ApiOperation("更新队伍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "队伍id", dataType = "String",required = true,paramType = "path"),
            @ApiImplicitParam(name = "teamInfo", value = "队伍信息", dataType = "TeamInfo",required = true)
    })
    public Result update(@PathVariable String id,@RequestBody TeamInfo team){
        teamService.update(id,team);
        return new Result(ResultCode.SUCCESS);
    }

    @RequiresRoles(value = {"student","teacher","admin"}, logical = Logical.OR)
    @GetMapping("/teams")
    @ApiOperation("队伍排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "起始页码，默认首页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示条数，默认4条", dataType = "int"),
            @ApiImplicitParam(name = "sort", value = "排序条件", dataType = "String"),
            @ApiImplicitParam(name = "sortType", value = "排序方式", dataType = "String")
    })
    public Result findByTypeAndLevelAndSort(Integer page,Integer size,
                                            @ApiParam(name = "sort", value = "排序条件",example = "createTime", required = true) @PathParam("sort") String sort,
                                            @ApiParam(name = "sortType", value = "升/降序",example = "asc",defaultValue = "desc")  @PathParam("sortType")String sortType) {
        if(page==null){
            page=1;
        }
        if(size==null){
            size=4;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("sort", sort);
        if(sortType==null){
            sortType="desc";
        }
        map.put("sortType",sortType);
        Page<Team> pageUser = teamService.findByTypeAndSort(page,size,map);
        PageResult<Team> pageResult = new PageResult<>(pageUser.getTotalElements(), pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }


}
