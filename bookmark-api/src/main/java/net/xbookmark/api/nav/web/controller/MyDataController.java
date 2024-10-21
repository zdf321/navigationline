package net.xbookmark.api.nav.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.xbookmark.api.nav.web.request.*;
import net.xbookmark.api.nav.web.response.MyDataCountResponse;
import net.xbookmark.api.nav.web.response.MyDirTreeResponse;
import net.xbookmark.api.nav.web.response.WebsiteInfoResponse;
import net.xbookmark.api.service.MyDataService;
import net.xbookmark.common.response.R;
import net.xbookmark.common.util.UserContext;
import net.xbookmark.core.domain.NavDatasDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: zigui.zdf @Date: 2022/3/10 21:27:07 @Description:
 */
@RestController
@RequestMapping("/my/data")
@Api(tags = "我的文件相关API")
public class MyDataController {

  @Autowired private MyDataService myDataService;

  @ApiOperation("加载数据")
  @GetMapping("/loadDatas")
  public R<NavDatasDomain> loadDatas(LoadMyDataRequest request) {
    final NavDatasDomain response = myDataService.loadDatas(request, UserContext.getUid());
    return R.success(response);
  }

  @ApiOperation("拖拽排序，只有sortType是0时支持")
  @PostMapping("/saveSort")
  public R<Boolean> saveSort(@Valid SaveSortRequest request) {
    final Boolean saveSort = myDataService.saveSort(request, UserContext.getUid());
    return R.success(saveSort);
  }

  @PostMapping("/move")
  @ApiOperation("移动")
  public R<Boolean> move(@Valid @RequestBody MoveDataRequest request) {
    final Boolean move = myDataService.move(request, UserContext.getUid());
    return R.success(move);
  }

  @PostMapping("/clone")
  @ApiOperation("复制/克隆")
  public R<Boolean> clone(@Valid @RequestBody MoveDataRequest request) {
    final Boolean clone = myDataService.clone(request, UserContext.getUid());
    return R.success(clone);
  }

  @PostMapping("/toTrash")
  @ApiOperation("删除-移入回收站")
  public R<Boolean> toTrash(@Valid @RequestBody RemoveDataRequest request) {
    final Boolean toTrash = myDataService.toTrash(request, UserContext.getUid());
    return R.success(toTrash);
  }

  @PostMapping("/removeFromTrash")
  @ApiOperation("彻底删除")
  public R<Boolean> removeFromTrash(@Valid @RequestBody RemoveDataRequest request) {
    final Boolean removeFromTrash = myDataService.removeFromTrash(request, UserContext.getUid());
    return R.success(removeFromTrash);
  }

  @PostMapping("/removeAllFromTrash")
  @ApiOperation("清空回收站")
  public R<Boolean> removeAllFromTrash() {
    final Boolean removeAllFromTrash = myDataService.removeAllFromTrash(UserContext.getUid());
    return R.success(removeAllFromTrash);
  }

  @PostMapping("/restore")
  @ApiOperation("还原")
  public R<Boolean> restore(@ApiParam(value = "数据id", required = true) @RequestParam String id) {
    final Boolean restore = myDataService.restore(id, UserContext.getUid());
    return R.success(restore);
  }

  @ApiOperation("保存目录")
  @PostMapping("/saveFolder")
  public R<Boolean> saveFolder(@Valid SaveFolderRequest request) {
    final Boolean saveFolder = myDataService.saveFolder(request, UserContext.getUid());
    return R.success(saveFolder);
  }

  @PostMapping("/saveWebsite")
  @ApiOperation(value = "保存网站")
  public R<Boolean> saveWebsite(@Valid SaveWebsiteRequest request) {
    final Boolean saveWebsite = myDataService.saveWebsite(request, UserContext.getUid());
    return R.success(saveWebsite);
  }

  @GetMapping("/getWebsiteInfo")
  @ApiOperation(value = "抓取网站信息")
  public R<WebsiteInfoResponse> getWebsiteInfo(
      @ApiParam(value = "网站地址", required = true) @RequestParam String url) {
    final WebsiteInfoResponse websiteInfo = myDataService.getWebsiteInfo(url);
    return R.success(websiteInfo);
  }

  @GetMapping("/getDirTree")
  @ApiOperation(value = "获取用户目录树")
  public R<MyDirTreeResponse> getDirTree(
      @ApiParam(value = "空间id", required = true) @RequestParam String spaceId) {
    final MyDirTreeResponse dirTree = myDataService.getDirTree(spaceId);
    return R.success(dirTree);
  }

  @GetMapping("/getFileCount")
  @ApiOperation(value = "获取文件数据")
  public R<MyDataCountResponse> getFileCount(
      @ApiParam(value = "团队id", required = true) @RequestParam String teamId) {
    final MyDataCountResponse fileCount = myDataService.getFileCount(teamId);
    return R.success(fileCount);
  }
}
