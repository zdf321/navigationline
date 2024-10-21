package net.xbookmark.api.nav.web.controller;

import io.swagger.annotations.ApiOperation;
import net.xbookmark.api.nav.web.response.HotWebsiteResponse;
import net.xbookmark.api.service.HotService;
import net.xbookmark.common.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/5/30 16:55
 */
//@RestController
//@RequestMapping("/hot")
//@Api(tags = "热门内容相关API")
public class HotController {

  @Autowired private HotService hotService;

  @GetMapping("/keyword")
  @ApiOperation(value = "热门关键词")
  public R<List<String>> getKeywords() {
    return R.success(hotService.keywords());
  }

  @GetMapping("/getHotWebsites")
  @ApiOperation(value = "热门网站")
  public R<List<HotWebsiteResponse>> getHotWebsites() {
    return R.success(hotService.getHotWebsites());
  }


}
