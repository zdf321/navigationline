package net.xbookmark.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.xbookmark.dao.model.NavCommonUseEntity;
import net.xbookmark.dao.model.NavEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/7/26 22:04
 */
public interface NavCommonUseMapper extends BaseMapper<NavCommonUseEntity> {

  List<NavEntity> getCommonUseList(
      @Param("uid") String uid,
      @Param("teamId") String teamId,
      @Param("search") String search,
      @Param("sortType") Integer sortType);
}
