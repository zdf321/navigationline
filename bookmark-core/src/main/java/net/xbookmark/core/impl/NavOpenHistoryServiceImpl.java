package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.core.NavOpenHistoryService;
import net.xbookmark.dao.mapper.NavOpenHistoryMapper;
import net.xbookmark.dao.model.NavEntity;
import net.xbookmark.dao.model.NavOpenHistoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【tb_nav_open_history(打开的历史记录表)】的数据库操作Service实现
 * @createDate 2023-08-06 20:59:42
 */
@Service
public class NavOpenHistoryServiceImpl
    extends ServiceImpl<NavOpenHistoryMapper, NavOpenHistoryEntity>
    implements NavOpenHistoryService {

  @Override
  public List<NavEntity> getRecentOpenList(String teamId, String uid) {
    return this.baseMapper.getRecentOpenList(teamId, uid);
  }
}
