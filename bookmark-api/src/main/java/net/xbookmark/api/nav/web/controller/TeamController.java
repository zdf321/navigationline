package net.xbookmark.api.nav.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xbookmark.api.nav.web.request.AddTeamRequest;
import net.xbookmark.api.nav.web.request.AddTeamSpaceRequest;
import net.xbookmark.api.nav.web.request.SetTeamInfoRequest;
import net.xbookmark.common.domain.TeamAndSpaceAuthDomain;
import net.xbookmark.common.domain.TeamMemberAndSpaceRoleDomain;
import net.xbookmark.common.domain.TeamMemberDomain;
import net.xbookmark.common.domain.TeamSpaceMemberDomain;
import net.xbookmark.common.enums.TeamSpaceTypeEnum;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.R;
import net.xbookmark.common.response.TableDataInfo;
import net.xbookmark.common.util.UserContext;
import net.xbookmark.core.NavService;
import net.xbookmark.core.TeamPublishInfoService;
import net.xbookmark.core.TeamService;
import net.xbookmark.core.TeamSpaceService;
import net.xbookmark.core.domain.*;
import net.xbookmark.dao.model.TeamPublishInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/7/16 17:26
 */
@RestController
@RequestMapping("/team")
@Api(tags = "团队相关接口")
public class TeamController {

  @Autowired private TeamService teamService;

  @Autowired private TeamSpaceService teamSpaceService;

  @Autowired private TeamPublishInfoService teamPublishInfoService;

  @Autowired private NavService navService;

  @GetMapping("/getTeamList")
  @ApiOperation("获取团队列表信息")
  public R<List<TeamInfoDomain>> getTeamList() {
    List<TeamInfoDomain> teamList = teamService.getTeamList(UserContext.getUid());
    return R.success(teamList);
  }

  @GetMapping("/getTeamInfo")
  @ApiOperation("获取团队信息")
  public R<TeamInfoDomain> getTeamInfo(@RequestParam String teamId) {
    String uid = UserContext.getUid();
    return R.success(teamService.getTeamInfo(uid, teamId));
  }

  @PostMapping("/addTeam")
  @ApiOperation("添加团队")
  public R<Boolean> addTeam(@Valid AddTeamRequest request) {
    teamService.addTeam(
        UserContext.getUid(),
        request.getOrgName(),
        request.getUserName(),
        request.getScale(),
        request.getBusinessType());

    return R.success(true);
  }

  @PostMapping("/addTeamSpace")
  @ApiOperation("添加团队空间")
  public R<Boolean> addTeamSpace(@Valid AddTeamSpaceRequest request) {
    String uid = UserContext.getUid();

    boolean validTeamId = teamService.validTeamId(uid, request.getTeamId());
    if (!validTeamId) {
      throw new BusinessException("空间创建失败");
    }

    teamSpaceService.addTeamSpace(
        request.getTeamId(),
        uid,
        request.getName(),
        request.getDesc(),
        null,
        TeamSpaceTypeEnum.OTHER,
        false);

    return R.success(true);
  }

  @GetMapping("/getTeamSpaceList")
  @ApiOperation("获取团队空间")
  public R<List<TeamSpaceInfoDomain>> getTeamSpaceList(@RequestParam String teamId) {
    List<TeamSpaceInfoDomain> teamSpaceList =
        teamSpaceService.getTeamSpaceList(teamId, UserContext.getUid());
    return R.success(teamSpaceList);
  }

  @GetMapping("/getMineSpace")
  @ApiOperation("获取我的空间")
  public R<TeamSpaceInfoDomain> getMineSpace(@RequestParam String teamId) {
    TeamSpaceInfoDomain mineTeam = teamSpaceService.getMineSpace(teamId, UserContext.getUid());
    return R.success(mineTeam);
  }

  @GetMapping("/getPersonalSpace")
  @ApiOperation("获取个人空间")
  public R<TeamSpaceInfoDomain> getPersonalSpace(@RequestParam String teamId) {
    TeamSpaceInfoDomain mineTeam = teamSpaceService.getPersonalSpace(teamId, UserContext.getUid());
    return R.success(mineTeam);
  }

  @PostMapping("/setInviteLink")
  @ApiOperation("设置团队邀请链接")
  public R<String> setInviteLink(
      @RequestParam String teamId, @RequestParam Boolean isOpen, @RequestParam Integer validDays) {
    return R.success(teamService.setInviteLink(teamId, isOpen, validDays, UserContext.getUid()));
  }

  @GetMapping("/getInviteInfo")
  @ApiOperation("获取团队邀请链接信息")
  public R<TeamInviteInfoDomain> getInviteInfo(@RequestParam String teamId) {
    return R.success(teamService.getInviteInfo(teamId, UserContext.getUid()));
  }

  @GetMapping("/getTeamInfoByInviteId")
  @ApiOperation("根据邀请链接id获取团队信息")
  public R<InviteTeamInfoDomain> getTeamInfoByInviteId(@RequestParam String inviteId) {
    return R.success(teamService.getTeamInfoByInviteId(inviteId));
  }

  @PostMapping("/addTeamMemeberByInviteId")
  @ApiOperation("根据邀请链接id添加成员")
  public R<Boolean> addTeamMemeberByInviteId(
      @RequestParam String inviteId, @RequestParam String nickname) {
    return R.success(
        teamService.addTeamMemeberByInviteId(inviteId, nickname, UserContext.getUid()));
  }

  @GetMapping("/getSpaceMemberPage")
  @ApiOperation("获取空间成员分页")
  public TableDataInfo<TeamSpaceMemberDomain> getSpaceMemberPage(
      @RequestParam(defaultValue = "1") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam String spaceId,
      String name) {
    return teamSpaceService.getSpaceMemberPage(pageNo, pageSize, spaceId, name);
  }

  @PostMapping("/setSpaceMemberRole")
  @ApiOperation("设置空间权限")
  public R<TeamSpaceMemberDomain> setSpaceMemberRole(
      @RequestParam String spaceId, @RequestParam String uid, String role) {
    return R.success(teamSpaceService.setSpaceMemberRole(spaceId, uid, role, UserContext.getUid()));
  }

  @GetMapping("/getMyTeamAndSpaceAuth")
  @ApiOperation("获取我的团队权限")
  public R<TeamAndSpaceAuthDomain> getMyTeamAndSpaceAuth(String teamId, String spaceId) {
    return R.success(teamSpaceService.getMyTeamAndSpaceAuth(teamId, spaceId, UserContext.getUid()));
  }

  @GetMapping("/getTeamMemberPage")
  @ApiOperation("获取团队成员分页")
  public TableDataInfo<TeamMemberDomain> getTeamMemberPage(
      @RequestParam(defaultValue = "1") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam String teamId,
      String name) {
    return teamSpaceService.getTeamMemberPage(pageNo, pageSize, teamId, name);
  }

  @GetMapping("/getTeamMemberList")
  @ApiOperation("获取团队成员列表")
  public R<List<TeamMemberDomain>> getTeamMemberList(@RequestParam String teamId, String name) {
    return R.success(teamSpaceService.getTeamMemberList(teamId, name));
  }

  @GetMapping("/getTeamMemberAndSpaceRoleInfo")
  @ApiOperation("获取团队内某个成员的信息")
  public R<TeamMemberAndSpaceRoleDomain> getTeamMemberAndSpaceRoleInfo(
      @RequestParam String teamId, @RequestParam String uid) {
    return R.success(teamSpaceService.getTeamMemberAndSpaceRoleInfo(teamId, uid));
  }

  @PostMapping("/setTeamMemberAndSpaceRoleInfo")
  @ApiOperation("设置团队内某个成员的信息")
  public R<Boolean> setTeamMemberAndSpaceRoleInfo(
      @RequestBody TeamMemberAndSpaceRoleDomain request) {
    return R.success(teamSpaceService.setTeamMemberAndSpaceRoleInfo(request, UserContext.getUid()));
  }

  @PostMapping("/removeTeamMemeber")
  @ApiOperation("移除团队成员")
  public R<Boolean> removeTeamMemeber(@RequestParam String teamId, @RequestParam String uid) {
    return R.success(teamSpaceService.removeTeamMemeber(teamId, uid, UserContext.getUid()));
  }

  @PostMapping("/setTeamInfo")
  @ApiOperation("设置团队信息")
  public R<Boolean> setTeamInfo(SetTeamInfoRequest request) {
    return R.success(
        teamService.setTeamInfo(
            request.getTeamId(),
            request.getTeamName(),
            request.getTeamNickname(),
            request.getOwnerUid()));
  }

  @PostMapping("/removeTeam")
  @ApiOperation("解散团队")
  public R<Boolean> removeTeam(@RequestParam String teamId) {
    return R.success(teamService.removeTeam(teamId, UserContext.getUid()));
  }

  @PostMapping("/setSpaceInfo")
  @ApiOperation("设置空间信息")
  public R<Boolean> setSpaceInfo(
      @RequestParam String spaceId, @RequestParam String name, String desc, String logoUrl) {
    return R.success(
        teamSpaceService.setSpaceInfo(spaceId, name, desc, logoUrl, UserContext.getUid()));
  }

  @PostMapping("/setSpaceWatermark")
  @ApiOperation("设置空间水印")
  public R<Boolean> setSpaceWatermark(
      @RequestParam String spaceId, @RequestParam Boolean showWatermark) {
    return R.success(
        teamSpaceService.setSpaceWatermark(spaceId, showWatermark, UserContext.getUid()));
  }

  @PostMapping("/removeSpace")
  @ApiOperation("删除空间")
  public R<Boolean> removeSpace(@RequestParam String spaceId, @RequestParam String name) {
    return R.success(teamSpaceService.removeSpace(spaceId, name, UserContext.getUid()));
  }

  @PostMapping("/setTeamPublishInfo")
  @ApiOperation("设置团队发布信息")
  public R<TeamPublishInfoEntity> setTeamPublishInfo(
      @RequestParam String teamId,
      @RequestParam Boolean published,
      @RequestParam Boolean showAd,
      @RequestParam Boolean checkAuth,
      @RequestParam Boolean needLogin) {
    return R.success(
        teamPublishInfoService.setTeamPublishInfo(
            teamId, published, showAd, checkAuth, needLogin, UserContext.getUid()));
  }

  @GetMapping("/getTeamPublishInfo")
  @ApiOperation("获取团队发布信息")
  public R<TeamPublishInfoEntity> getTeamPublishInfo(@RequestParam String teamId) {
    return R.success(teamPublishInfoService.getTeamPublishInfo(teamId));
  }

  @GetMapping("/getTeamPublishDatas")
  @ApiOperation("获取团队发布后的数据")
  public R<TeamPublishDataDomain> getTeamPublishDatas(@RequestParam String publishId) {
    return R.success(teamPublishInfoService.getTeamPublishDatas(publishId));
  }

  @GetMapping("/getPublishDatasByFolderId")
  @ApiOperation("根据文件夹id获取数据")
  public R<NavDatasDomain> getPublishDatasByFolderId(@RequestParam String folderId) {
    NavDatasDomain navDatasDomain = navService.getByFolderId(folderId);
    return R.success(navDatasDomain);
  }

  @GetMapping("/searchTeamPublishDatasPage")
  @ApiOperation("分页搜索团队发布后的数据")
  public TableDataInfo<NavDataItemDomain> searchTeamPublishDatasPage(
      @RequestParam String publishId,
      String search,
      @RequestParam(defaultValue = "1") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    return navService.searchTeamPublishDatasPage(publishId, search, pageNo, pageSize);
  }
}
