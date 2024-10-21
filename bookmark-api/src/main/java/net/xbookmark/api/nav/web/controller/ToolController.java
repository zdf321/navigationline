//package net.xbookmark.api.nav.web.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import net.xbookmark.api.nav.web.request.CollectRequest;
//import net.xbookmark.api.nav.web.response.NavCategoryResponse;
//import net.xbookmark.api.nav.web.response.NavResponse;
//import net.xbookmark.api.nav.web.response.NavSearchResponse;
//import net.xbookmark.api.service.ToolService;
//import net.xbookmark.common.response.R;
//import net.xbookmark.common.util.UserContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
///** @Author: zigui.zdf @Date: 2022/3/11 21:38:10 @Description: */
//@RestController
//@RequestMapping("/tool")
//@Api(tags = "工具相关API")
//public class ToolController {
//
//  @Autowired private ToolService toolService;
//
//  @GetMapping("/loadCategory")
//  @ApiOperation(("获取导航分类"))
//  public R<List<NavCategoryResponse>> loadCategory() {
//    final List<NavCategoryResponse> responses = toolService.loadCategory();
//    return R.success(responses);
//  }
//
//  @GetMapping("/loadTool")
//  @ApiOperation(value = "获取工具导航数据")
//  public R<List<NavResponse>> loadNav(
//      @ApiParam(value = "父id", required = true) @RequestParam String pid,
//      @ApiParam(value = "第几页", defaultValue = "1")
//          @RequestParam(required = false, defaultValue = "1")
//          Integer pageNum) {
//    final List<NavResponse> navResponses = toolService.loadNav(pid, pageNum);
//    return R.success(navResponses);
//  }
//
//  @GetMapping("/searchTool")
//  @ApiOperation(value = "搜索工具")
//  public R<List<NavSearchResponse>> searchTool(
//      @ApiParam("搜索内容") String search,
//      @ApiParam(value = "第几页", defaultValue = "1")
//          @RequestParam(required = false, defaultValue = "1")
//          Integer pageNum) {
//    final List<NavSearchResponse> navSearchResponses = toolService.searchNav(search, pageNum);
//    return R.success(navSearchResponses);
//  }
//
//  @PostMapping("/collect")
//  @ApiOperation(value = "收藏，需登录")
//  public R<Boolean> collect(@Valid CollectRequest request) {
//    final Boolean collect = toolService.collect(request, UserContext.getUid());
//    return R.success(collect);
//  }
//}
