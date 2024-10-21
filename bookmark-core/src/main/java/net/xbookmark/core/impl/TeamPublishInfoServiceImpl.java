package net.xbookmark.core.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.common.enums.TeamRoleEnum;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.StatusCode;
import net.xbookmark.common.util.UserContext;
import net.xbookmark.core.*;
import net.xbookmark.core.domain.*;
import net.xbookmark.dao.mapper.TeamPublishInfoMapper;
import net.xbookmark.dao.model.TeamEntity;
import net.xbookmark.dao.model.TeamMemberEntity;
import net.xbookmark.dao.model.TeamPublishInfoEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author DELL
 * @description 针对表【tb_team_publish_info(团队发布信息)】的数据库操作Service实现
 * @createDate 2023-10-09 21:48:31
 */
@Service
public class TeamPublishInfoServiceImpl
    extends ServiceImpl<TeamPublishInfoMapper, TeamPublishInfoEntity>
    implements TeamPublishInfoService {

  @Autowired private TeamMemberService teamMemberService;

  @Autowired private NavService navService;

  @Autowired private TeamSpaceService teamSpaceService;

  @Autowired private NavCommonUseService navCommonUseService;

  @Autowired private TeamService teamService;

  @Override
  public void checkNeedLogin(String publishId) {
    TeamPublishInfoEntity publishInfo = this.getById(publishId);
    if (null == publishInfo || !publishInfo.getPublished()) {
      throw new BusinessException("该团队尚未对外发布，请联系团队管理员发布后再进行访问");
    }

    Boolean needLogin = Optional.ofNullable(publishInfo.getNeedLogin()).orElse(false);
    Boolean checkAuth = Optional.ofNullable(publishInfo.getCheckAuth()).orElse(false);

    String uid = UserContext.getUid();

    if (needLogin || checkAuth) {
      if (StringUtils.isBlank(uid)) {
        throw new BusinessException(StatusCode.NO_LOGIN, "请先登录");
      }
    }
  }

  @Override
  public TeamPublishInfoEntity setTeamPublishInfo(
      String teamId,
      Boolean published,
      Boolean showAd,
      Boolean checkAuth,
      Boolean needLogin,
      String uid) {
    TeamMemberEntity memberEntity = teamMemberService.getByTeamIdAndUid(teamId, uid);
    if (!TeamRoleEnum.hasTeamManageAuth(memberEntity.getTeamRole())) {
      throw new BusinessException("无权进行此操作");
    }

    TeamPublishInfoEntity teamPublishInfo = getTeamPublishInfo(teamId);
    if (null == teamPublishInfo) {
      teamPublishInfo = new TeamPublishInfoEntity();
      teamPublishInfo.setTeamId(teamId);
      teamPublishInfo.setPublished(published);
      teamPublishInfo.setShowAd(showAd);
      teamPublishInfo.setCheckAuth(checkAuth);
      teamPublishInfo.setNeedLogin(needLogin);

      // 生成domain
      String domain = String.format("https://%s.navigationline.cn", generateDomain());
      teamPublishInfo.setDomain(domain);
      this.save(teamPublishInfo);
    } else {
      teamPublishInfo.setPublished(published);
      teamPublishInfo.setShowAd(showAd);
      teamPublishInfo.setCheckAuth(checkAuth);
      teamPublishInfo.setNeedLogin(needLogin);
      this.updateById(teamPublishInfo);
    }
    return teamPublishInfo;
  }

  @Override
  public TeamPublishInfoEntity getTeamPublishInfo(String teamId) {
    TeamPublishInfoEntity publishInfo =
        this.getOne(
            new LambdaQueryWrapper<TeamPublishInfoEntity>()
                .eq(TeamPublishInfoEntity::getTeamId, teamId));
    return publishInfo;
  }

  @Override
  public String generateDomain() {
    String uuid = "";
    long count = 1;

    while (count > 0) {
      uuid = UUID.fastUUID().toString(true).substring(0, 6);
      count =
          this.count(
              new LambdaQueryWrapper<TeamPublishInfoEntity>()
                  .eq(TeamPublishInfoEntity::getDomain, uuid));
    }
    return uuid;
  }

  @Override
  public TeamPublishDataDomain getTeamPublishDatas(String publishId) {
    checkNeedLogin(publishId);

    TeamPublishInfoEntity publishInfo = this.getById(publishId);

    String teamId = publishInfo.getTeamId();
    Boolean checkAuth = Optional.ofNullable(publishInfo.getCheckAuth()).orElse(false);

    String uid = UserContext.getUid();

    TeamPublishDataDomain dataDomain = new TeamPublishDataDomain();
    List<TeamPublishRowDomain> rowList = new ArrayList<>();
    TeamPublishRowDomain mySpaceRow = null;
    List<NavDataItemDomain> myCommonUseFileList = null;

    TeamEntity teamEntity = teamService.getById(teamId);
    if (null == teamEntity) {
      throw new BusinessException("该团队已解散");
    }

    // ------------------------团队空间内容----------------------
    List<TeamSpaceInfoDomain> teamSpaceList =
        checkAuth
            ? teamSpaceService.getTeamSpaceList(teamId, uid)
            : teamSpaceService.getTeamSpaceList(teamId);
    for (TeamSpaceInfoDomain teamSpaceInfoDomain : teamSpaceList) {
      TeamPublishRowDomain rowDomain = getFromSpace(teamSpaceInfoDomain);
      if (!Objects.isNull(rowDomain)) {
        rowList.add(rowDomain);
      }
    }
    // ------------------------团队空间内容----------------------

    // ------------------------我的空间内容----------------------
    if (StringUtils.isNotBlank(uid)) {
      TeamSpaceInfoDomain mineSpace = teamSpaceService.getMineSpace(teamId, uid);
      mySpaceRow = getFromSpace(mineSpace);
    }
    // ------------------------我的空间内容----------------------

    // ------------------------我的常用内容----------------------
    if (StringUtils.isNotBlank(uid)) {
      NavDatasDomain commonUseList = navCommonUseService.getCommonUseList(uid, teamId, null, null);
      myCommonUseFileList = commonUseList.getFiles();
    }
    // ------------------------我的常用内容----------------------

    dataDomain.setTeamId(teamEntity.getId());
    dataDomain.setTeamName(teamEntity.getOrgName());
    dataDomain.setRowList(rowList);
    dataDomain.setMySpaceRow(mySpaceRow);
    dataDomain.setMyCommonUseFileList(myCommonUseFileList);

    return dataDomain;
  }

  private TeamPublishRowDomain getFromSpace(TeamSpaceInfoDomain teamSpaceInfoDomain) {
    if (Objects.isNull(teamSpaceInfoDomain)) {
      return null;
    }
    TeamPublishRowDomain rowDomain = new TeamPublishRowDomain();
    rowDomain.setSpaceId(teamSpaceInfoDomain.getSpaceId());
    rowDomain.setSpaceName(teamSpaceInfoDomain.getName());
    rowDomain.setSpaceType(teamSpaceInfoDomain.getSpaceType());
    rowDomain.setSpaceLogoUrl(teamSpaceInfoDomain.getLogoUrl());

    NavDatasDomain datasDomain = navService.getBySpaceId(teamSpaceInfoDomain.getSpaceId());
    rowDomain.setFolders(datasDomain.getFolders());
    rowDomain.setFiles(datasDomain.getFiles());

    // 特殊处理：如果空间下有文件并且同时有文件夹，则把空间名也当做文件夹放到folders里面
    if (CollectionUtils.isNotEmpty(datasDomain.getFiles())
        && CollectionUtils.isNotEmpty(datasDomain.getFolders())) {
      NavDataItemDomain spaceFolder = new NavDataItemDomain();
      spaceFolder.setId(teamSpaceInfoDomain.getSpaceId());
      spaceFolder.setName(teamSpaceInfoDomain.getName());
      rowDomain.getFolders().add(0, spaceFolder);
    }

    // 如果空间下没有文件但是有文件夹，则取第一个文件夹下的内容
    if (CollectionUtils.isEmpty(datasDomain.getFiles())
        && CollectionUtils.isNotEmpty(datasDomain.getFolders())) {
      NavDatasDomain datasDomain1 =
          navService.getByFolderId(datasDomain.getFolders().get(0).getId());
      List<NavDataItemDomain> files = new ArrayList<>();
      if (CollectionUtils.isNotEmpty(datasDomain1.getFolders())) {
        files.addAll(datasDomain1.getFolders());
      }
      if (CollectionUtils.isNotEmpty(datasDomain1.getFiles())) {
        files.addAll(datasDomain1.getFiles());
      }
      rowDomain.setFiles(files);
    }

    return rowDomain;
  }
}
