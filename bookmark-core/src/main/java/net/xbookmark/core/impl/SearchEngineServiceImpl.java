package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.common.util.ConvertUtil;
import net.xbookmark.core.SearchEngineDetailService;
import net.xbookmark.core.SearchEngineService;
import net.xbookmark.core.domain.NavSearchCategoryDomain;
import net.xbookmark.dao.mapper.SearchEngineEntityMapper;
import net.xbookmark.dao.model.SearchEngineDetailEntity;
import net.xbookmark.dao.model.SearchEngineEntity;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author DELL
 * @description 针对表【tb_search_engine(搜索引擎配置主表)】的数据库操作Service实现
 * @createDate 2023-07-29 17:33:30
 */
@Service
public class SearchEngineServiceImpl
    extends ServiceImpl<SearchEngineEntityMapper, SearchEngineEntity>
    implements SearchEngineService {

  @Autowired private SearchEngineDetailService searchEngineDetailService;

  @Override
  public List<NavSearchCategoryDomain> loadSearchCategory() {
    List<SearchEngineEntity> entities =
        this.list(
            new LambdaQueryWrapper<SearchEngineEntity>().orderByAsc(SearchEngineEntity::getSort));

    List<SearchEngineDetailEntity> detailEntities = searchEngineDetailService.list();

    Map<String, List<SearchEngineDetailEntity>> detailMap =
        detailEntities.stream()
            .collect(Collectors.groupingBy(SearchEngineDetailEntity::getSearchEngineId));

    final List<NavSearchCategoryDomain> list = new ArrayList<>();

    for (SearchEngineEntity entity : entities) {
      final NavSearchCategoryDomain domain =
          ConvertUtil.copyProperties(entity, NavSearchCategoryDomain.class);

      List<SearchEngineDetailEntity> childs = detailMap.get(entity.getId());

      if (CollectionUtils.isNotEmpty(childs)) {
        childs.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));

        final List<NavSearchCategoryDomain.Child> children =
            ConvertUtil.copyProperties(childs, NavSearchCategoryDomain.Child.class);

        domain.setChilds(children);
      }
      list.add(domain);
    }

    return list;
  }
}
