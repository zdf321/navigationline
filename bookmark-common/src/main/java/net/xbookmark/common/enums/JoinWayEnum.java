package net.xbookmark.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangdingfei
 * @date 2023/9/4 13:12
 */
@AllArgsConstructor
@Getter
public enum JoinWayEnum {
  DEFAULT(0, "默认"),
  INVITE_LINK(1, "链接邀请"),
  INVITE_ACCOUNT(2, "账号直接邀请");

  Integer code;
  String desc;
}
