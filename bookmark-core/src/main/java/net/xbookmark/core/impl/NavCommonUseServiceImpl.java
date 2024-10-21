package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.common.service.CommonService;
import net.xbookmark.core.NavCommonUseService;
import net.xbookmark.core.domain.NavDatasDomain;
import net.xbookmark.dao.mapper.NavCommonUseMapper;
import net.xbookmark.dao.model.NavCommonUseEntity;
import net.xbookmark.dao.model.NavEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/7/26 22:20
 */
@Service
public class NavCommonUseServiceImpl extends ServiceImpl<NavCommonUseMapper, NavCommonUseEntity>
    implements NavCommonUseService {

  @Autowired
  private CommonService commonService;

  @Override
  public NavDatasDomain getCommonUseList(
      String uid, String teamId, String search, Integer sortType) {

    List<NavEntity> commonUseList = this.baseMapper.getCommonUseList(uid, teamId, search, sortType);

    NavDatasDomain navDatasDomain = commonService.convertFromNavList(commonUseList, uid, teamId);
    navDatasDomain.setSortType(sortType);

    return navDatasDomain;
  }

  @Override
  public int getNextSort(String teamId) {
    NavCommonUseEntity entity =
        this.getOne(
            new LambdaQueryWrapper<NavCommonUseEntity>()
                .eq(NavCommonUseEntity::getTeamId, teamId)
                .orderByDesc(NavCommonUseEntity::getSort)
                .last("limit 1"));

    if (null == entity) {
      return 1;
    }

    return entity.getSort() + 1;
  }
}
