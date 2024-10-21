package net.xbookmark.api.nav.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xbookmark.api.nav.web.request.*;
import net.xbookmark.api.nav.web.response.UserInfoResponse;
import net.xbookmark.api.service.UserService;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.R;
import net.xbookmark.common.response.StatusCode;
import net.xbookmark.common.util.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/1 17:24
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关API")
public class UserController {

  @Autowired private UserService userService;

  @PostMapping("/register")
  @ApiOperation(value = "注册")
  public R<Boolean> register(
      @Valid @RequestBody RegisterRequest param, HttpServletRequest request) {
    final Boolean register = userService.register(param, request);
    return R.success(register);
  }

  @PostMapping("/login")
  @ApiOperation(value = "登陆")
  public R<Boolean> login(
      @Valid @RequestBody LoginRequest param,
      HttpServletRequest request,
      HttpServletResponse response) {
    final Integer loginType = param.getLoginType();
    if (loginType == 0) {
      if (StringUtils.isBlank(param.getPassword())) {
        throw new BusinessException(StatusCode.FAILED, "密码必填");
      }
    }

    if (loginType == 1) {
      if (StringUtils.isBlank(param.getCode())) {
        throw new BusinessException(StatusCode.FAILED, "验证码必填");
      }
    }

    final Boolean login = userService.login(param, request, response);

    return R.success(login);
  }

  @GetMapping("/logout")
  @ApiOperation(value = "登出")
  public R<Boolean> logout(HttpServletResponse response) {
    final Boolean logout = userService.logout(response);
    return R.success(logout);
  }

  @GetMapping("/getUserInfo")
  @ApiOperation(value = "获取用户信息")
  public R<UserInfoResponse> getUserInfo() {
    final String uid = UserContext.getUid();
    if (null == uid) {
      return R.success(null);
    }
    final UserInfoResponse userInfo = userService.getUserInfo(uid);
    return R.success(userInfo);
  }

  @PostMapping("/saveUserInfo")
  @ApiOperation(value = "保存用户信息")
  public R<Boolean> saveUserInfo(@RequestBody SaveUserInfoRequest request) {
    String uid = UserContext.getUid();
    return R.success(userService.saveUserInfo(request, uid));
  }

  @PostMapping("/updatePassword")
  @ApiOperation(value = "修改密码")
  public R<Boolean> updatePassword(@Valid @RequestBody UpdatePwdRequest request) {
    String uid = UserContext.getUid();
    return R.success(userService.updatePassword(request, uid));
  }
}
