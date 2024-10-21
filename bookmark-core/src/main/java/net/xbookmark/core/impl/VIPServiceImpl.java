package net.xbookmark.core.impl;

import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.util.Constants;
import net.xbookmark.core.NavService;
import net.xbookmark.core.VIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/** @Author: zigui.zdf @Date: 2022/3/28 21:43:47 @Description: */
@Service
@Slf4j
public class VIPServiceImpl implements VIPService {

  @Autowired private RedisTemplate redisTemplate;

  @Autowired private NavService navService;

  @Override
  public long getUserFileCount(String uid) {
    String key = String.format(Constants.RedisKey.USER_FILE_COUNT_PREFIX, uid);

    final Object o = redisTemplate.opsForValue().get(key);

    if (null == o) {
      final long fileCount = navService.getFileCount(uid);
      redisTemplate.opsForValue().set(key, fileCount, 1, TimeUnit.DAYS);
      return fileCount;
    }

    return Long.valueOf(o.toString());
  }

  @Override
  public void increaseUserFileCount(String uid) {
    String key = String.format(Constants.RedisKey.USER_FILE_COUNT_PREFIX, uid);

    redisTemplate.opsForValue().increment(key);
  }

  @Override
  public void decreaseUserFileCount(String uid) {
    String key = String.format(Constants.RedisKey.USER_FILE_COUNT_PREFIX, uid);

    redisTemplate.opsForValue().decrement(key);
  }
}
