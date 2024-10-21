package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.core.SearchEngineDetailService;
import net.xbookmark.dao.mapper.SearchEngineDetailEntityMapper;
import net.xbookmark.dao.model.SearchEngineDetailEntity;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【tb_search_engine_detail(搜索引擎配置表)】的数据库操作Service实现
* @createDate 2023-07-29 17:33:30
*/
@Service
public class SearchEngineDetailServiceImpl extends ServiceImpl<SearchEngineDetailEntityMapper, SearchEngineDetailEntity>
    implements SearchEngineDetailService {

}




