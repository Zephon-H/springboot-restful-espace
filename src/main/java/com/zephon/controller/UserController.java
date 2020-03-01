package com.zephon.controller;

import com.zephon.domain.User;
import com.zephon.domain.request.LoginInfo;
import com.zephon.domain.response.PageResult;
import com.zephon.domain.response.ProfileResult;
import com.zephon.entity.JWTToken;
import com.zephon.entity.Result;
import com.zephon.entity.ResultCode;
import com.zephon.service.UserService;
import com.zephon.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.controller.system
 * @date 2020/2/28 下午2:24
 * @Copyright ©
 */
@CrossOrigin
@RestController
@Api(value = "用户相关接口", tags = "用户相关接口")
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录",notes = "手机号和密码必须输入")
    public Result login(@RequestBody LoginInfo loginInfo) {
//        String mobile = map.get("mobile");
//        String password = map.get("password");
        String mobile = loginInfo.getMobile();
        String password = loginInfo.getPassword();
        password = new Md5Hash(password, mobile, 3).toString();
        System.out.println(password);

            try {
                Map<String, Object> m = new HashMap<>();
                m.put("mobile", mobile);
                m.put("password", password);
                String token = jwtUtil.createJwt(null, null, m);
                JWTToken jwtToken = new JWTToken(token);
                Subject subject = SecurityUtils.getSubject();
                subject.login(jwtToken);
                return new Result(ResultCode.SUCCESS, "Bearer " + token);
            } catch (AuthenticationException e) {
                return new Result(ResultCode.MOBILEORPASSWORDERROR);
            }
    }

    @RequiresRoles(value = {"student", "teacher", "admin"}, logical = Logical.OR)
    @ApiOperation("根据token自动获取用户信息")
    @GetMapping("/profile")
    public Result profile() {
        // 获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //1.subject获取所有的安全数据集合
        PrincipalCollection principals = subject.getPrincipals();
        // 获取安全数据
        ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();
        return new Result(ResultCode.SUCCESS, result);
    }

    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @GetMapping("/users")
    @ApiOperation("查询所有用户 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "起始页码，默认首页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示条数，默认4条", dataType = "int")
    })
    public Result findAll(Integer page, Integer size) {
        if(page==null){
            page=1;
        }
        if(size==null){
            size=4;
        }
        Page<User> pageUser = userService.findAll(page, size);
        PageResult<User> pageResult = new PageResult<>(pageUser.getTotalElements(), pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    @RequiresRoles(value = {"student", "teacher", "admin"}, logical = Logical.OR)
    @GetMapping("/user/team")
    @ApiOperation("根据用户id和队伍id将用户加入队伍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String",required = true),
            @ApiImplicitParam(name = "teamId", value = "队伍id", dataType = "String",required = true)
    })
    public Result addTeam(@RequestParam("userId") String userId, @RequestParam("teamId") String teamId) {
        userService.addTeam(userId, teamId);
        return new Result(ResultCode.SUCCESS);
    }

    @PostMapping("/user/upload/{id}")
    @RequiresRoles(value = {"student", "teacher", "admin"}, logical = Logical.OR)
    @ApiOperation("上传头像，返回头像urldata")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户 id", dataType = "String",required = true),
            @ApiImplicitParam(name = "file", value = "上传的头像图片文件", dataType = "MultipartFile",required = true)
    })
    public Result upload(@PathVariable String id, @RequestParam(name = "file") MultipartFile file) throws IOException {
        // 保存后，获取到图片地址回显
        String imgUrl = userService.uploadImage(id, file);
        return new Result(ResultCode.SUCCESS, imgUrl);
    }
}
