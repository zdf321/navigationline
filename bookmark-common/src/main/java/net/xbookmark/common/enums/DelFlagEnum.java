package net.xbookmark.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangdingfei
 * @date 2023/6/19 17:08
 */
@AllArgsConstructor
@Getter
public enum DelFlagEnum {

    ENABLE("0", "启用"),
  DISABLE("1", "禁用");

    String code;

    String desc;
}
