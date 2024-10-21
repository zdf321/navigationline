package net.xbookmark.api.nav.web.controller;

import io.swagger.annotations.ApiOperation;
import net.xbookmark.common.response.R;
import net.xbookmark.core.SearchEngineService;
import net.xbookmark.core.domain.NavSearchCategoryDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/10/5 21:28
 */
@RestController
@RequestMapping("/p")
public class PublishController {

  @Autowired private SearchEngineService searchEngineService;

  @GetMapping("/getSearchCategories")
  @ApiOperation(value = "加载搜索分类")
  public R<List<NavSearchCategoryDomain>> getSearchCategories() {
    return R.success(searchEngineService.loadSearchCategory());
  }
}
