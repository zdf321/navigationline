package net.xbookmark.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.xbookmark.dao.model.NavEntity;
import net.xbookmark.dao.model.NavOpenHistoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/7/26 22:04
 */
public interface NavOpenHistoryMapper extends BaseMapper<NavOpenHistoryEntity> {

  /**
   * 获取最近打开记录
   *
   * @param teamId
   * @param uid
   * @return
   */
  List<NavEntity> getRecentOpenList(@Param("teamId") String teamId, @Param("uid") String uid);
}
