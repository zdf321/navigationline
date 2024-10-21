package net.xbookmark.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.xbookmark.dao.model.SystemNavEntity;

/**
 * @Author: zigui.zdf @Date: 2022/3/15 20:50:00 @Description:
 */
public interface SystemNavMapper extends BaseMapper<SystemNavEntity> {

  /**
   * 增加收藏量
   *
   * @param id
   */
  default void increaseCollectNum(String id) {}

  /**
   * 增加浏览量
   *
   * @param id
   */
  default void increaseHitsNum(String id) {}
}
