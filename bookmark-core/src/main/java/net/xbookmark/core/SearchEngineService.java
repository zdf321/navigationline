package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.core.domain.NavSearchCategoryDomain;
import net.xbookmark.dao.model.SearchEngineEntity;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【tb_search_engine(搜索引擎配置主表)】的数据库操作Service
 * @createDate 2023-07-29 17:33:30
 */
public interface SearchEngineService extends IService<SearchEngineEntity> {
  List<NavSearchCategoryDomain> loadSearchCategory();
}
