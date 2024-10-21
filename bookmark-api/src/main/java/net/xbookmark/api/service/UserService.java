package net.xbookmark.api.service;

import net.xbookmark.api.nav.web.request.*;
import net.xbookmark.api.nav.web.response.UserInfoResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/1 20:49
 */
public interface UserService {

  Boolean register(RegisterRequest param, HttpServletRequest request);

  Boolean login(LoginRequest param, HttpServletRequest request, HttpServletResponse response);

  Boolean logout(HttpServletResponse response);

  UserInfoResponse getUserInfo(String uid);

  Boolean saveUserInfo(SaveUserInfoRequest request, String uid);

  Boolean updatePassword(UpdatePwdRequest request, String uid);
}
