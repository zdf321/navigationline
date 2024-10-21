package net.xbookmark.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangdingfei
 * @date 2023/7/5 12:22
 */
@AllArgsConstructor
@Getter
public enum CodeTypeEnum {
  REGISTER(0, "注册"),
  LOGIN(1, "登录"),
  COMMON(2, "通用"),
  ;

  int code;

  String desc;

  public static CodeTypeEnum getByCode(int code) {
    for (CodeTypeEnum value : CodeTypeEnum.values()) {
      if (value.code == code) {
        return value;
      }
    }
    return null;
  }
}
