package net.xbookmark.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 成员规模枚举
 *
 * @author zhangdingfei
 * @date 2023/7/16 17:19
 */
@AllArgsConstructor
@Getter
public enum TeamScaleEnum {
  SCALE_1_10("1-10人"),
  SCALE_11_20("11-20人"),
  SCALE_21_50("21-50人"),
  SCALE_51_100("51-100人"),
  SCALE_101_200("101-200人"),
  SCALE_201_500("201-500人"),
  SCALE_501_1000("501-1000人"),
  SCALE_1001_2000("1001-2000人"),
  SCALE_2001("2000人以上"),
  ;

  private String name;

  public static TeamScaleEnum getByCode(String code) {
    for (TeamScaleEnum value : values()) {
      if (value.name().equals(code)) {
        return value;
      }
    }

    return null;
  }
}
