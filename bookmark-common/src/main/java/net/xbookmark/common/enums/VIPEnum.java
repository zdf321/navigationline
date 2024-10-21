package net.xbookmark.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * VIP枚举
 *
 * @author zhangdingfei
 * @date 2023/7/16 22:05
 */
@AllArgsConstructor
@Getter
public enum VIPEnum {
  P_FREE("免费版"),
  P_VIP("VIP版"),
  P_VIP_TEAM("团队个人版"),
  T_TRIAL("开源版"),
  T_VIP("VIP版"),
  T_EXPIRED("已过期");

  private String name;

  public static VIPEnum getByType(String vipType) {
    for (VIPEnum value : VIPEnum.values()) {
      if (StringUtils.equals(value.name(), vipType)) {
        return value;
      }
    }
    return null;
  }
}
