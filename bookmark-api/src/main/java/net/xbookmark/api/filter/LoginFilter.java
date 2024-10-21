package net.xbookmark.api.filter;

import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.response.StatusCode;
import net.xbookmark.common.util.JwtUtil;
import net.xbookmark.common.util.UserContext;
import net.xbookmark.common.util.matcher.AntPathMatcher;
import net.xbookmark.common.util.matcher.PatternMatcher;
import net.xbookmark.common.util.matcher.WebUtils;
import net.xbookmark.common.vo.UserInfo;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/3 21:32
 */
@Slf4j
public class LoginFilter implements Filter {

  private PatternMatcher pathMatcher = new AntPathMatcher();

  // 匿名访问的接口
  private CopyOnWriteArrayList<String> anonPathList = new CopyOnWriteArrayList<>();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    UserInfo userInfo = doAuthentication(request, response);

    // 匿名URI，不校验
    if (isAnonPath(request)) {
      this.doFilter(servletRequest, servletResponse, chain, userInfo);
      return;
    }

    if (userInfo == null) {
      printAuthcFail(response);
      return;
    }

    this.doFilter(servletRequest, servletResponse, chain, userInfo);
  }

  private void doFilter(
      ServletRequest servletRequest,
      ServletResponse servletResponse,
      FilterChain chain,
      UserInfo userInfo)
      throws IOException, ServletException {
    try {
      UserContext.putUser(userInfo);
      chain.doFilter(servletRequest, servletResponse);
    } finally {
      UserContext.destory();
    }
  }

  private UserInfo doAuthentication(HttpServletRequest request, HttpServletResponse response) {
    String jwtTokenString = JwtUtil.getJwtTokenString(request);
    // 无JWT，未登陆
    if (jwtTokenString == null || jwtTokenString.length() == 0) {
      return null;
    }
    UserInfo userInfo = JwtUtil.parseToken(jwtTokenString);

    if (userInfo == null) {
      return null;
    }

    // tokenVo过期，未登陆
    if (JwtUtil.isTokenExpired(userInfo)) {
      return null;
    }

    // 每过1小时 自动给token续期
    Date date = DateUtils.addHours(new Date(), 1);
    if (date.compareTo(userInfo.getExpire()) > 0) {
      JwtUtil.refreshToken(userInfo, response);
    }
    return userInfo;
  }

  private boolean isAnonPath(HttpServletRequest request) {
    for (String path : this.anonPathList) {
      String requestURI = WebUtils.getPathWithinApplication(request);
      if (pathMatcher.matches(path, requestURI)) {
        return true;
      }
    }
    return false;
  }

  private void printAuthcFail(HttpServletResponse response) {
    try {
      response.setContentType("application/json;charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write("{\"status\":\"" + StatusCode.NO_LOGIN + "\",\"msg\":\"未登陆\"}");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public void setAnonPath(Collection<String> anonPathCol) {
    if (anonPathCol == null || anonPathCol.isEmpty()) {
      return;
    }
    synchronized (this) {
      List<String> deleteList =
          this.anonPathList.stream()
              .filter(e -> !anonPathCol.contains(e))
              .distinct()
              .collect(Collectors.toList());
      if (deleteList != null && !deleteList.isEmpty()) {
        this.anonPathList.removeAll(deleteList);
      }
      this.anonPathList.addAllAbsent(anonPathCol);
    }
  }

  @Override
  public void destroy() {}
}
