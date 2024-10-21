package net.xbookmark.common.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/10/31 23:20
 */
@Data
public class UserInfo {

  private String uid;

  private boolean isVIP;

  private Date expire;

  private Date createTime;
}
