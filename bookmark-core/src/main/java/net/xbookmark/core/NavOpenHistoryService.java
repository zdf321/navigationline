package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.dao.model.NavEntity;
import net.xbookmark.dao.model.NavOpenHistoryEntity;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【tb_nav_open_history(打开的历史记录表)】的数据库操作Service
 * @createDate 2023-08-06 20:59:42
 */
public interface NavOpenHistoryService extends IService<NavOpenHistoryEntity> {

  List<NavEntity> getRecentOpenList(String teamId, String uid);

}
