package net.xbookmark.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zigui.zdf @Date: 2022/3/13 9:05:22 @Description:
 */
@Slf4j
public class NavUtil {
  private static final Pattern phonePattern = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}");

  private static final Pattern emailPattern =
      Pattern.compile("^\\w+@[\\da-z\\.-]+\\.([a-z]{2,6}|[\\u2E80-\\u9FFF]{2,3})$");

  private static final Pattern chinesePatter = Pattern.compile("[\\u4e00-\\u9fff]");

  public static boolean isPhone(String address) {
    if (StringUtils.isBlank(address)) {
      return false;
    }
    final Matcher matcher = phonePattern.matcher(address);

    return matcher.matches();
  }

  public static boolean isEmail(String address) {
    if (StringUtils.isBlank(address)) {
      return false;
    }
    final Matcher matcher = emailPattern.matcher(address);

    return matcher.matches();
  }

  public static String urlEncodeChinese(String url) {
    try {
      Matcher matcher = chinesePatter.matcher(url);
      String tmp;
      while (matcher.find()) {
        tmp = matcher.group();
        url = url.replaceAll(tmp, URLEncoder.encode(tmp, "UTF-8"));
      }
    } catch (UnsupportedEncodingException e) {
      log.error("url encode 失败", e);
    }
    return url.replace(" ", "%20");
  }

  public static void main(String[] args) {
    String url = "https://www.navigationline.cn/team/invitation/index?inviteId=52ceab4f7c2e42baac643ac58edb2dd3&name=饭饭#token=null";

    System.out.println(urlEncodeChinese(url));
  }
}
