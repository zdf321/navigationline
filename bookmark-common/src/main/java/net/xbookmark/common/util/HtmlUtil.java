package net.xbookmark.common.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/28 20:40
 */
public class HtmlUtil {

  private static final String title = "(?is)<title>([\\s\\S]*?)</title>";

  private static final String description =
      "(?is)<meta\\s*name=\"description\"\\s*content=\"([\\s\\S]*?)\"";

  private static final String keywords =
      "(?is)<meta\\s*name=\"keywords\"\\s*content=\"([\\s\\S]*?)\"";

  private static final String icon =
      "(?is)<link\\s*rel=\"(shortcut)?\\s*icon\"\\s*href=\"([\\s\\S]*?)\"";

  private static final String icon2 =
      "(?is)<link\\s*href=\"([\\s\\S]*?)\"\\s*rel=\"(shortcut)?\\s*icon\"";

  private static final Pattern titlePattern = Pattern.compile(title);

  private static final Pattern descriptionPattern = Pattern.compile(description);

  private static final Pattern keywordsPattern = Pattern.compile(keywords);

  private static final Pattern iconPattern = Pattern.compile(icon);

  private static final Pattern iconPattern2 = Pattern.compile(icon2);

  public static String getTitle(String html) {
    Matcher matcher = titlePattern.matcher(html);

    if (matcher.find()) {
      return matcher.group(1);
    }

    return null;
  }

  public static String getDescription(String html) {
    Matcher matcher = descriptionPattern.matcher(html);

    if (matcher.find()) {
      return matcher.group(1);
    }

    return null;
  }

  public static String getKeywords(String html) {
    Matcher matcher = keywordsPattern.matcher(html);

    if (matcher.find()) {
      return matcher.group(1);
    }

    return null;
  }

  public static String getIcon(String html) {
    Matcher matcher = iconPattern.matcher(html);

    if (matcher.find()) {
      return matcher.group(2);
    }

    matcher = iconPattern2.matcher(html);
    if (matcher.find()) {
      return matcher.group(1);
    }

    return "/favicon.ico";
  }

  public static void main(String[] args) {
    String url = "https://www.baidu.com/favicon.ico";

    HttpResponse httpResponse = HttpRequest.get(url).timeout(4000).execute();
    if (httpResponse.getStatus() != 200) {
      return;
    }

    String html = httpResponse.body();

    if (StringUtils.isNotBlank(html)) {
      String title = HtmlUtil.getTitle(html);
      String description = HtmlUtil.getDescription(html);
      final String keywords = HtmlUtil.getKeywords(html);
      String icon = HtmlUtil.getIcon(html);

      System.out.println(title);
      System.out.println(description);
      System.out.println(keywords);
      System.out.println(icon);
    }
  }
}
