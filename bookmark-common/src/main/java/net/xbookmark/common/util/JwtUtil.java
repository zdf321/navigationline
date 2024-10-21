package net.xbookmark.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.vo.UserInfo;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
public class JwtUtil {

  public static UserInfo parseToken(String token) {
    try {
      Claims claims =
          Jwts.parser().setSigningKey(Constants.JWT_SECRET_KEY).parseClaimsJws(token).getBody();
      if (claims == null) {
        return null;
      }
      UserInfo userInfo = new UserInfo();
      userInfo.setExpire(claims.get("expire", Date.class));
      userInfo.setCreateTime(claims.get("createTime", Date.class));
      userInfo.setUid(claims.get("uid", String.class));
      userInfo.setVIP(claims.get("isVIP", Boolean.class));
      return userInfo;
    } catch (Exception e) {
      log.error("parse jwt token {} error", token, e);
    }
    return null;
  }

  public static String createToken(UserInfo userInfo) {
    return Jwts.builder()
        .setSubject(Constants.JWT_SUBJECT_NAV)
        .claim("expire", oneWeekLater())
        .claim("createTime", now())
        .claim("uid", userInfo.getUid())
        .claim("isVIP", userInfo.isVIP())
        .signWith(SignatureAlgorithm.HS256, Constants.JWT_SECRET_KEY)
        .compact();
  }

  public static boolean isTokenExpired(UserInfo tokenVo) {
    return tokenVo.getExpire().before(now());
  }

  public static String refreshToken(UserInfo userInfo, HttpServletResponse response) {
    String ticket = createToken(userInfo);

    setCookie(
        response,
        Constants.DOMAIN,
        Constants.JWT_TOKEN,
        ticket,
        Constants.JWT_TOKEN_TIMEOUT * 60 * 60);

    return ticket;
  }

  public static void deleteToken(HttpServletResponse response) {
    deleteCookie(response, Constants.DOMAIN, Constants.JWT_TOKEN);
  }

  public static String getJwtTokenString(HttpServletRequest request) {
    String token = getCookieValue(request, Constants.JWT_TOKEN);
    if (token == null || token.length() == 0) {
      token = request.getParameter(Constants.JWT_TOKEN);
    }
    return token;
  }

  public static Cookie getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length == 0) {
      return null;
    }
    for (Cookie cookie : cookies) {
      String cookieName = cookie.getName();
      if (cookieName != null && cookieName.equals(name)) {
        return cookie;
      }
    }
    return null;
  }

  private static String getCookieValue(HttpServletRequest request, String name) {
    Cookie cookie = getCookie(request, name);
    if (cookie == null) {
      return null;
    }
    return cookie.getValue();
  }

  public static HttpServletResponse setCookie(
      HttpServletResponse response, String domain, String name, String value, int time) {
    Cookie cookie = new Cookie(name, value);
    if (!StringUtils.isEmpty(domain)) {
      cookie.setDomain(domain);
    }
    cookie.setPath("/");
    cookie.setMaxAge(time);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);
    return response;
  }

  private static void deleteCookie(HttpServletResponse response, String domain, String name) {
    Cookie cookie = new Cookie(name, null);
    if (!StringUtils.isEmpty(domain)) {
      cookie.setDomain(domain);
    }
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  private static Date oneWeekLater() {
    return Date.from(Instant.now().plus(Constants.JWT_TOKEN_TIMEOUT, ChronoUnit.HOURS));
  }

  private static Date now() {
    return Date.from(Instant.now());
  }
}
