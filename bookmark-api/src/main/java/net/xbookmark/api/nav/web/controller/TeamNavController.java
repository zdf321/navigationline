package net.xbookmark.api.nav.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xbookmark.api.nav.web.request.AddCommonUseFromPublishRequest;
import net.xbookmark.api.nav.web.request.AddCommonUseRequest;
import net.xbookmark.api.nav.web.response.TeamDirTreeResponse;
import net.xbookmark.api.service.TeamNavService;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.R;
import net.xbookmark.common.service.CommonService;
import net.xbookmark.common.util.UserContext;
import net.xbookmark.core.NavCommonUseService;
import net.xbookmark.core.NavOpenHistoryService;
import net.xbookmark.core.NavService;
import net.xbookmark.core.TeamPublishInfoService;
import net.xbookmark.core.domain.NavDatasDomain;
import net.xbookmark.core.domain.WebsiteDetailDomain;
import net.xbookmark.dao.model.NavCommonUseEntity;
import net.xbookmark.dao.model.NavEntity;
import net.xbookmark.dao.model.NavOpenHistoryEntity;
import net.xbookmark.dao.model.TeamPublishInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/7/30 14:22
 */
@RestController
@RequestMapping("/team/nav")
@Api(tags = "团队nav相关接口")
public class TeamNavController {

  @Autowired private NavCommonUseService navCommonUseService;

  @Autowired private NavOpenHistoryService navOpenHistoryService;

  @Autowired private CommonService commonService;

  @Autowired private TeamNavService teamNavService;

  @Autowired private NavService navService;

  @Autowired private TeamPublishInfoService teamPublishInfoService;

  @GetMapping("/getCommonUseList")
  @ApiOperation(value = "获取我的常用")
  public R<NavDatasDomain> getCommonUseList(
      @RequestParam String teamId,
      @RequestParam(defaultValue = "0", required = false) Integer sortType,
      String search) {
    String uid = UserContext.getUid();

    NavDatasDomain commonUseList =
        navCommonUseService.getCommonUseList(uid, teamId, search, sortType);

    return R.success(commonUseList);
  }

  @PostMapping("/addCommonUse")
  @ApiOperation(value = "添加我的常用")
  public R<Boolean> addCommonUse(@Valid AddCommonUseRequest request) {
    String teamId = request.getTeamId();
    String navId = request.getNavId();

    long count =
        navCommonUseService.count(
            new LambdaQueryWrapper<NavCommonUseEntity>()
                .eq(NavCommonUseEntity::getTeamId, teamId)
                .eq(NavCommonUseEntity::getNavId, navId));
    if (count > 0) {
      throw new BusinessException("该资源已在我的常用");
    }

    NavCommonUseEntity entity = new NavCommonUseEntity();
    entity.setUid(UserContext.getUid());
    entity.setTeamId(teamId);
    entity.setNavId(navId);
    entity.setSort(navCommonUseService.getNextSort(teamId));
    navCommonUseService.save(entity);

    return R.success(true);
  }

  @PostMapping("/addCommonUsePublish")
  @ApiOperation(value = "添加我的常用")
  public R<Boolean> addCommonUsePublish(@Valid AddCommonUseFromPublishRequest request) {
    String navId = request.getNavId();
    String publishId = request.getPublishId();

    TeamPublishInfoEntity publishInfoEntity = teamPublishInfoService.getById(publishId);
    String teamId = publishInfoEntity.getTeamId();

    AddCommonUseRequest addCommonUseRequest = new AddCommonUseRequest();
    addCommonUseRequest.setNavId(navId);
    addCommonUseRequest.setTeamId(teamId);

    addCommonUse(addCommonUseRequest);

    return R.success(true);
  }

  @PostMapping("/removeCommonUse")
  @ApiOperation(value = "移除我的常用")
  public R<Boolean> removeCommonUse(@Valid AddCommonUseRequest request) {
    String teamId = request.getTeamId();
    String navId = request.getNavId();

    navCommonUseService.remove(
        new LambdaQueryWrapper<NavCommonUseEntity>()
            .eq(NavCommonUseEntity::getNavId, navId)
            .eq(NavCommonUseEntity::getTeamId, teamId)
            .eq(NavCommonUseEntity::getUid, UserContext.getUid()));

    return R.success(true);
  }

  @PostMapping("/removeCommonUsePublish")
  @ApiOperation(value = "移除我的常用--发布态")
  public R<Boolean> removeCommonUsePublish(@Valid AddCommonUseFromPublishRequest request) {
    String navId = request.getNavId();
    String publishId = request.getPublishId();

    TeamPublishInfoEntity publishInfoEntity = teamPublishInfoService.getById(publishId);
    String teamId = publishInfoEntity.getTeamId();

    navCommonUseService.remove(
        new LambdaQueryWrapper<NavCommonUseEntity>()
            .eq(NavCommonUseEntity::getNavId, navId)
            .eq(NavCommonUseEntity::getTeamId, teamId)
            .eq(NavCommonUseEntity::getUid, UserContext.getUid()));

    return R.success(true);
  }

  @GetMapping("/getRecentOpenList")
  @ApiOperation(value = "获取最近打开")
  public R<NavDatasDomain> getRecentOpen(@RequestParam String teamId) {
    String uid = UserContext.getUid();

    List<NavEntity> recentOpenList = navOpenHistoryService.getRecentOpenList(teamId, uid);

    NavDatasDomain myDataResponse = commonService.convertFromNavList(recentOpenList, uid, teamId);

    return R.success(myDataResponse);
  }

  @PostMapping("/addOpenHistory")
  @ApiOperation(value = "添加访问记录")
  public R<Boolean> addOpenHistory(@RequestParam String navId) {

    NavEntity navEntity = navService.getById(navId);
    if (null != navEntity) {
      NavOpenHistoryEntity entity = new NavOpenHistoryEntity();
      entity.setTeamId(navEntity.getTeamId());
      entity.setUid(UserContext.getUid());
      entity.setNavId(navId);
      navOpenHistoryService.save(entity);

      navService.increseHitsNum(navId);
    }

    return R.success(true);
  }

  @GetMapping("/getDirTree")
  @ApiOperation(value = "获取团队空间用户目录树")
  public R<List<TeamDirTreeResponse>> getTeamDirTree(@RequestParam String teamId) {
    return R.success(teamNavService.getDirTree(UserContext.getUid(), teamId));
  }

  @GetMapping("/getAllDirTree")
  @ApiOperation(value = "获取当前用户所有有空间操作权限的目录树")
  public R<List<TeamDirTreeResponse>> getAllDirTree() {
    return R.success(teamNavService.getDirTree(UserContext.getUid()));
  }

  @GetMapping("/getWebsiteDetail")
  @ApiOperation(value = "获取网站详情")
  public R<WebsiteDetailDomain> getWebsiteDetail(
      @RequestParam String publishId, @RequestParam String navId) {
    return R.success(navService.getWebsiteDetail(publishId, navId));
  }
}
