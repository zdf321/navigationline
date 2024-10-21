package net.xbookmark.common.util;

import net.xbookmark.common.vo.UserInfo;

public class UserContext {

  private static final ThreadLocal<UserInfo> ACCOUNT_THREAD_LOCAL = new ThreadLocal<>();

  public static void putUser(UserInfo tokenVo) {
    ACCOUNT_THREAD_LOCAL.set(tokenVo);
  }

  public static String getUid() {
    UserInfo jwtTokenVo = ACCOUNT_THREAD_LOCAL.get();
    if (jwtTokenVo == null) {
      return null;
    }
    return jwtTokenVo.getUid();
  }

  public static UserInfo getUserInfo() {
    return ACCOUNT_THREAD_LOCAL.get();
  }

  public static void destory() {
    ACCOUNT_THREAD_LOCAL.remove();
  }
}
