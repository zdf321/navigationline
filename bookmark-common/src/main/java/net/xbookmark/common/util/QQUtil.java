package net.xbookmark.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/6/29 22:14
 */
@Slf4j
public class QQUtil {

  public static Token getToken(String code, String appId, String appKey, String redirectUrl) {
    OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).build();

    String url =
        String.format(
            "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&fmt=json",
            appId, appKey, code, redirectUrl);

    final Request request = new Request.Builder().url(url).build();

    try (final Response response = httpClient.newCall(request).execute()) {
      String body = new String(response.body().bytes());

      final Token token = JSONObject.parseObject(body, Token.class);

      return token;

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      httpClient.connectionPool().evictAll();
    }

    return null;
  }

  public static String getOpenId(String accessToken) {
    OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).build();

    String url =
        String.format("https://graph.qq.com/oauth2.0/me?access_token=%s&fmt=json", accessToken);

    final Request request = new Request.Builder().url(url).build();

    try (final Response response = httpClient.newCall(request).execute()) {
      String body = new String(response.body().bytes());

      final OpenId openId = JSONObject.parseObject(body, OpenId.class);

      return openId.getOpenid();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      httpClient.connectionPool().evictAll();
    }

    return null;
  }

  public static UserInfo getUserInfo(String accessToken, String appId, String openId) {
    OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).build();

    String url =
        String.format(
            "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s",
            accessToken, appId, openId);

    final Request request = new Request.Builder().url(url).build();

    try (final Response response = httpClient.newCall(request).execute()) {
      String body = new String(response.body().bytes());

      final UserInfo userInfo = JSONObject.parseObject(body, UserInfo.class);

      return userInfo;

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      httpClient.connectionPool().evictAll();
    }

    return null;
  }

  @Data
  public static class Token {

    private String access_token;

    private Long expires_in;

    private String refresh_token;

    private String code;

    private String msg;
  }

  @Data
  private static class OpenId {

    private String client_id;

    private String openid;
  }

  @Data
  public static class UserInfo {

    /** 返回码 0-正确 */
    private int ret;

    /** 如果ret>0，会有相应的错误信息提示，返回数据全部用UTF-8编码。 */
    private String msg;

    /** 用户在QQ空间的昵称。 */
    private String nickname;

    /** 大小为30×30像素的QQ空间头像URL。 */
    private String figureurl;

    /** 大小为50×50像素的QQ空间头像URL。 */
    private String figureurl_1;

    /** 大小为100×100像素的QQ空间头像URL。 */
    private String figureurl_2;

    /** 大小为40×40像素的QQ头像URL。 */
    private String figureurl_qq_1;

    /** 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。 */
    private String figureurl_qq_2;

    /** 性别。 如果获取不到则默认返回"男" */
    private String gender;
  }
}
