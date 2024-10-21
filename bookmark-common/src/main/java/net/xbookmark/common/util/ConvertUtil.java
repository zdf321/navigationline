package net.xbookmark.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ConvertUtil {

  public static <T> T copyProperties(Object sourceObj, Class<T> targetClass) {
    if (sourceObj == null || targetClass == null) {
      return null;
    }
    try {
      T targetObj = targetClass.newInstance();
      BeanUtils.copyProperties(sourceObj, targetObj);
      return targetObj;
    } catch (Exception e) {
      log.error(
          "copy bean error, from {} to {}",
          sourceObj.getClass().getSimpleName(),
          targetClass.getSimpleName(),
          e);
    }
    return null;
  }

  public static <T> T copyProperties(
      Object sourceObj, Class<T> targetClass, String... ignoreProperties) {
    if (sourceObj == null || targetClass == null) {
      return null;
    }
    try {
      T targetObj = targetClass.newInstance();
      BeanUtils.copyProperties(sourceObj, targetObj, ignoreProperties);
      return targetObj;
    } catch (Exception e) {
      log.error(
          "copy bean error, from {} to {}",
          sourceObj.getClass().getSimpleName(),
          targetClass.getSimpleName(),
          e);
    }
    return null;
  }

  public static <T> List<T> copyProperties(
      List<? extends Object> sourceObjList, Class<T> targetClass) {
    if (sourceObjList == null || sourceObjList.isEmpty() || targetClass == null) {
      return Collections.emptyList();
    }

    return sourceObjList.stream()
        .map(e -> copyProperties(e, targetClass))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  /**
   * 将long类型的数值转为带单位的字符串
   *
   * @param size
   * @return
   */
  public static String sizeFormatNum2String(Long size) {

    if (size == null) {
      return "0.00KB";
    }

    boolean flag = false;
    if (size.longValue() < 0) {
      size = 0 - size;
      flag = true;
    }
    String s;
    if (size.longValue() > 1024L * 1024L * 1024L) {
      s = String.format("%.2f", (double) size.longValue() / (1024L * 1024L * 1024L)) + "GB";
    } else if (size.longValue() > 1024L * 1024L) {
      s = String.format("%.2f", (double) size.longValue() / 1048576) + "MB";
    } else if (size.longValue() > 1024) {
      s = String.format("%.2f", (double) size.longValue() / 1024) + "KB";
    } else {
      s = String.format("%.2f", (double) size.longValue()) + "B";
    }

    if (flag && StringUtils.isNotBlank(s)) {
      s = "-" + s;
    }
    return s;
  }

  public static void main(String... args) {
    //    Object obj = new Object();
    //    copyProperties(obj, Object.class);
    //    System.out.println(obj);
  }
}
