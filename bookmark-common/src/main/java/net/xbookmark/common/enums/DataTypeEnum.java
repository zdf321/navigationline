package net.xbookmark.common.enums;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/1 10:30
 */
public enum DataTypeEnum {
  DIR(1, "目录"),
  WEBSITE(2, "网站"),
  FILE(3, "文件");

  private Integer dataType;

  private String desc;

  DataTypeEnum(Integer dataType, String desc) {
    this.dataType = dataType;
    this.desc = desc;
  }

  public Integer getDataType() {
    return dataType;
  }

  public String getDesc() {
    return desc;
  }

  public static String getDesc(Integer nodeType) {
    for (DataTypeEnum value : DataTypeEnum.values()) {
      if (Objects.equals(value.getDataType(), nodeType)) {
        return value.desc;
      }
    }
    return null;
  }

  public static DataTypeEnum of(int dataType) {
    for (DataTypeEnum value : DataTypeEnum.values()) {
      if (Objects.equals(value.getDataType(), dataType)) {
        return value;
      }
    }
    return null;
  }

  public static DataTypeEnum of(String dataTypeDesc) {
    for (DataTypeEnum value : DataTypeEnum.values()) {
      if (StringUtils.equals(value.desc, dataTypeDesc)) {
        return value;
      }
    }
    return null;
  }

  public static List<Integer> getFileTypes() {
    List<Integer> fileTypes = Lists.newArrayList(WEBSITE.dataType, FILE.dataType);
    return fileTypes;
  }
}
