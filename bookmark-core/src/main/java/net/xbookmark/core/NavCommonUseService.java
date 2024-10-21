package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.core.domain.NavDatasDomain;
import net.xbookmark.dao.model.NavCommonUseEntity;

/**
 * @author zhangdingfei
 * @date 2023/7/26 22:10
 */
public interface NavCommonUseService extends IService<NavCommonUseEntity> {

  /**
   * 获取我的常用列表
   *
   * @param uid
   * @param teamId
   * @param search
   * @param sortType
   * @return
   */
  NavDatasDomain getCommonUseList(String uid, String teamId, String search, Integer sortType);

  /**
   * 获取下一个排序值
   *
   * @param teamId
   * @return
   */
  int getNextSort(String teamId);
}
