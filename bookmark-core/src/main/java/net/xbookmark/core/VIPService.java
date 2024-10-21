package net.xbookmark.core;

/** @Author: zigui.zdf @Date: 2022/3/28 21:42:45 @Description: */
public interface VIPService {

  long getUserFileCount(String uid);

  void increaseUserFileCount(String uid);

  void decreaseUserFileCount(String uid);
}
