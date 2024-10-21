package net.xbookmark.core.domain;

import lombok.Data;

/** @Author: zigui.zdf @Date: 2022/3/22 23:10:02 @Description: */
@Data
public class SendSmsCodeDomain {

  private String key;

  private long time;
}
