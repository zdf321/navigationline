package net.xbookmark.common.response;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/10/31 22:56
 */
public class StatusCode {

  /** 成功 */
  public static final String SUCCESS = "1";

  /** 失败 */
  public static final String FAILED = "2";

  /** 未登录 */
  public static final String NO_LOGIN = "3";

  /** 文件数不足 */
  public static final String NO_FILE_COUNT_LEFT = "4";

  /**
   * 资源不存在-404
   */
  public static final String NO_RESOURCE = "5";
}
