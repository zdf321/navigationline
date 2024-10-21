package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.domain.*;
import net.xbookmark.common.enums.DelFlagEnum;
import net.xbookmark.common.enums.TeamRoleEnum;
import net.xbookmark.common.enums.TeamSpaceRoleEnum;
import net.xbookmark.common.enums.TeamSpaceTypeEnum;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.TableDataInfo;
import net.xbookmark.core.TeamMemberService;
import net.xbookmark.core.TeamSpaceMemberService;
import net.xbookmark.core.TeamSpaceService;
import net.xbookmark.core.domain.TeamSpaceInfoDomain;
import net.xbookmark.dao.mapper.TeamSpaceMapper;
import net.xbookmark.dao.model.TeamMemberEntity;
import net.xbookmark.dao.model.TeamSpaceEntity;
import net.xbookmark.dao.model.TeamSpaceMemberEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangdingfei
 * @date 2023/7/18 14:55
 */
@Service
@Slf4j
public class TeamSpaceServiceImpl extends ServiceImpl<TeamSpaceMapper, TeamSpaceEntity>
    implements TeamSpaceService {

  @Autowired private TeamSpaceMemberService teamSpaceMemberService;

  @Autowired private TeamMemberService teamMemberService;

  @Override
  @Transactional
  public String addTeamSpace(
      String teamId,
      String uid,
      String name,
      String desc,
      Integer maxFileCount,
      TeamSpaceTypeEnum teamSpaceTypeEnum,
      Boolean showWatermark) {

    // 如果空间类型是MINE，则判断一下数据是否存在
    if (TeamSpaceTypeEnum.MINE.equals(teamSpaceTypeEnum)) {
      TeamSpaceEntity mineSpace =
          this.getOne(
              new LambdaQueryWrapper<TeamSpaceEntity>()
                  .eq(TeamSpaceEntity::getTeamId, teamId)
                  .eq(TeamSpaceEntity::getUid, uid)
                  .eq(TeamSpaceEntity::getSpaceType, TeamSpaceTypeEnum.MINE.name()));
      if (null != mineSpace) {
        return mineSpace.getId();
      }
    }

    // 添加空间
    TeamSpaceEntity entity = new TeamSpaceEntity();
    entity.setTeamId(teamId);
    entity.setUid(uid);
    entity.setName(name);
    entity.setDesc(desc);
    entity.setMaxFileCount(maxFileCount);
    entity.setSpaceType(teamSpaceTypeEnum.name());
    entity.setShowWatermark(showWatermark);
    this.save(entity);

    // 默认将当前用户置为拥有者
    TeamSpaceMemberEntity memberEntity = new TeamSpaceMemberEntity();
    memberEntity.setSpaceId(entity.getId());
    memberEntity.setUid(uid);
    memberEntity.setSpaceRole(TeamSpaceRoleEnum.OWNER.name());
    memberEntity.setJoinTime(new Date());
    teamSpaceMemberService.save(memberEntity);

    return entity.getId();
  }

  @Override
  public List<TeamSpaceInfoDomain> getTeamSpaceList(String teamId, String uid) {
    List<TeamSpaceEntity> list =
        this.list(
            new LambdaQueryWrapper<TeamSpaceEntity>()
                .eq(TeamSpaceEntity::getTeamId, teamId)
                .in(
                    TeamSpaceEntity::getSpaceType,
                    Lists.newArrayList(
                        TeamSpaceTypeEnum.PUBLIC.name(), TeamSpaceTypeEnum.OTHER.name())));

    List<String> spaceIds = list.stream().map(TeamSpaceEntity::getId).collect(Collectors.toList());

    if (CollectionUtils.isEmpty(spaceIds)) {
      return Collections.EMPTY_LIST;
    }

    List<TeamSpaceMemberEntity> spaceMemberEntities =
        teamSpaceMemberService.list(
            new LambdaQueryWrapper<TeamSpaceMemberEntity>()
                .eq(TeamSpaceMemberEntity::getUid, uid)
                .in(TeamSpaceMemberEntity::getSpaceId, spaceIds));

    Map<String, TeamSpaceMemberEntity> spaceMemberEntityMap =
        spaceMemberEntities.stream().collect(Collectors.toMap(x -> x.getSpaceId(), x -> x));

    List<TeamSpaceInfoDomain> domainList = new ArrayList<>();

    for (TeamSpaceEntity entity : list) {
      if (!spaceMemberEntityMap.containsKey(entity.getId())) {
        continue;
      }
      TeamSpaceInfoDomain infoDomain =
          TeamSpaceInfoDomain.builder()
              .spaceId(entity.getId())
              .name(entity.getName())
              .desc(entity.getDesc())
              .logoUrl(entity.getLogoUrl())
              .showWatermark(entity.getShowWatermark())
              .spaceType(entity.getSpaceType())
              .spaceRole(spaceMemberEntityMap.get(entity.getId()).getSpaceRole())
              .build();
      domainList.add(infoDomain);
    }

    return domainList;
  }

  @Override
  public List<TeamSpaceInfoDomain> getTeamSpaceList(String teamId) {
    List<TeamSpaceEntity> list =
        this.list(
            new LambdaQueryWrapper<TeamSpaceEntity>()
                .eq(TeamSpaceEntity::getTeamId, teamId)
                .in(
                    TeamSpaceEntity::getSpaceType,
                    Lists.newArrayList(
                        TeamSpaceTypeEnum.PUBLIC.name(), TeamSpaceTypeEnum.OTHER.name())));

    List<String> spaceIds = list.stream().map(TeamSpaceEntity::getId).collect(Collectors.toList());

    if (CollectionUtils.isEmpty(spaceIds)) {
      return Collections.EMPTY_LIST;
    }

    List<TeamSpaceInfoDomain> domainList = new ArrayList<>();

    for (TeamSpaceEntity entity : list) {
      TeamSpaceInfoDomain infoDomain =
          TeamSpaceInfoDomain.builder()
              .spaceId(entity.getId())
              .name(entity.getName())
              .desc(entity.getDesc())
              .logoUrl(entity.getLogoUrl())
              .showWatermark(entity.getShowWatermark())
              .spaceType(entity.getSpaceType())
              .build();
      domainList.add(infoDomain);
    }

    return domainList;
  }

  @Override
  public TeamSpaceInfoDomain getMineSpace(String teamId, String uid) {
    TeamSpaceEntity teamSpace =
        this.getOne(
            new LambdaQueryWrapper<TeamSpaceEntity>()
                .eq(TeamSpaceEntity::getTeamId, teamId)
                .eq(TeamSpaceEntity::getUid, uid)
                .eq(TeamSpaceEntity::getSpaceType, TeamSpaceTypeEnum.MINE.name())
                .last("limit 1"));

    if (null != teamSpace) {
      return TeamSpaceInfoDomain.builder()
          .spaceId(teamSpace.getId())
          .name(teamSpace.getName())
          .spaceType(TeamSpaceTypeEnum.MINE.name())
          .build();
    }
    return null;
  }

  @Override
  public TeamSpaceInfoDomain getPersonalSpace(String teamId, String uid) {
    TeamSpaceEntity teamSpace =
        this.getOne(
            new LambdaQueryWrapper<TeamSpaceEntity>()
                .eq(TeamSpaceEntity::getTeamId, teamId)
                .eq(TeamSpaceEntity::getUid, uid)
                .eq(TeamSpaceEntity::getSpaceType, TeamSpaceTypeEnum.PERSONAL.name())
                .last("limit 1"));

    if (null != teamSpace) {
      return TeamSpaceInfoDomain.builder()
          .spaceId(teamSpace.getId())
          .name(teamSpace.getName())
          .build();
    }
    return null;
  }

  @Override
  public TeamSpaceInfoDomain getPublicSpace(String teamId) {
    TeamSpaceEntity teamSpace =
        this.getOne(
            new LambdaQueryWrapper<TeamSpaceEntity>()
                .eq(TeamSpaceEntity::getTeamId, teamId)
                .eq(TeamSpaceEntity::getSpaceType, TeamSpaceTypeEnum.PUBLIC.name()));

    if (null != teamSpace) {
      return TeamSpaceInfoDomain.builder()
          .spaceId(teamSpace.getId())
          .name(teamSpace.getName())
          .build();
    }
    return null;
  }

  @Override
  public TableDataInfo<TeamSpaceMemberDomain> getSpaceMemberPage(
      Integer pageNo, Integer pageSize, String spaceId, String name) {
    IPage<TeamSpaceMemberDomain> page = new Page(pageNo, pageSize);
    IPage<TeamSpaceMemberDomain> spaceMemberPage =
        teamMemberService.getSpaceMemberPage(page, spaceId, name);
    return TableDataInfo.build(spaceMemberPage);
  }

  @Override
  public TeamSpaceMemberDomain setSpaceMemberRole(
      String spaceId, String uid, String role, String currentUid) {
    if (Objects.equals(uid, currentUid)) {
      throw new BusinessException("无权进行此操作");
    }

    TeamSpaceMemberEntity member =
        teamSpaceMemberService.getOne(
            new LambdaQueryWrapper<TeamSpaceMemberEntity>()
                .eq(TeamSpaceMemberEntity::getSpaceId, spaceId)
                .eq(TeamSpaceMemberEntity::getUid, currentUid));

    if (null == member) {
      throw new BusinessException("无权进行此操作");
    }

    TeamSpaceEntity teamSpaceEntity = this.getById(spaceId);

    TeamAndSpaceAuthDomain myTeamAndSpaceAuth =
        this.getMyTeamAndSpaceAuth(teamSpaceEntity.getTeamId(), spaceId, currentUid);

    if (!myTeamAndSpaceAuth.getHasAuthToManageSpace()) {
      throw new BusinessException("无权进行此操作");
    }

    teamSpaceMemberService.saveSpaceMember(spaceId, uid, role);

    return teamMemberService.getSpaceMember(spaceId, uid);
  }

  @Override
  public TeamAndSpaceAuthDomain getMyTeamAndSpaceAuth(String teamId, String spaceId, String uid) {
    TeamAndSpaceAuthDomain domain = new TeamAndSpaceAuthDomain();
    domain.setUid(uid);

    if (StringUtils.isNotBlank(teamId)) {
      TeamMemberEntity teamMember = teamMemberService.getByTeamIdAndUid(teamId, uid);
      String teamRole = teamMember == null ? null : teamMember.getTeamRole();
      boolean hasTeamManageAuth = TeamRoleEnum.hasTeamManageAuth(teamRole);

      domain.setTeamRole(teamRole);
      if (hasTeamManageAuth) {
        domain.setHasAuthToManageTeam(true);
        domain.setHasAuthToManageSpace(true);
        domain.setHasAuthToEditSpace(true);
      }
    }

    if (StringUtils.isNotBlank(spaceId)) {
      String spaceRole = teamSpaceMemberService.getSpaceRole(spaceId, uid);
      boolean hasSpaceManageAuth = TeamSpaceRoleEnum.hasSpaceManageAuth(spaceRole);
      boolean hasSpaceEditorAuth = TeamSpaceRoleEnum.hasSpaceEditorAuth(spaceRole);

      domain.setSpaceRole(spaceRole);
      if (hasSpaceManageAuth) {
        domain.setHasAuthToManageSpace(true);
        domain.setHasAuthToEditSpace(true);
      }

      if (hasSpaceEditorAuth) {
        domain.setHasAuthToEditSpace(true);
      }
    }

    return domain;
  }

  @Override
  public TableDataInfo<TeamMemberDomain> getTeamMemberPage(
      Integer pageNo, Integer pageSize, String teamId, String name) {
    IPage<TeamMemberDomain> page = new Page(pageNo, pageSize);

    return TableDataInfo.build(teamMemberService.getTeamMemberPage(page, teamId, name));
  }

  @Override
  public List<TeamMemberDomain> getTeamMemberList(String teamId, String name) {
    IPage<TeamMemberDomain> page = new Page(1, Integer.MAX_VALUE);
    IPage<TeamMemberDomain> teamMemberPage =
        teamMemberService.getTeamMemberPage(page, teamId, name);
    return teamMemberPage.getRecords();
  }

  @Override
  public TeamMemberAndSpaceRoleDomain getTeamMemberAndSpaceRoleInfo(String teamId, String uid) {
    TeamMemberAndSpaceRoleDomain domain = new TeamMemberAndSpaceRoleDomain();

    TeamMemberEntity teamMemberEntity = teamMemberService.getByTeamIdAndUid(teamId, uid);
    if (null == teamMemberEntity) {
      return domain;
    }

    domain.setUid(uid);
    domain.setTeamNickname(teamMemberEntity.getNickname());
    domain.setRole(teamMemberEntity.getTeamRole());

    List<SpaceRoleDomain> spaceRoleDomains = this.baseMapper.selectSpaceRoleInfo(teamId, uid);
    domain.setSpaceRoles(spaceRoleDomains);

    return domain;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean setTeamMemberAndSpaceRoleInfo(
      TeamMemberAndSpaceRoleDomain request, String currentUid) {
    TeamMemberEntity teamMemberEntity =
        teamMemberService.getByTeamIdAndUid(request.getTeamId(), currentUid);

    if (Objects.equals(teamMemberEntity.getTeamRole(), request.getRole())) {
      throw new BusinessException("无权进行此操作");
    }

    TeamMemberEntity update = new TeamMemberEntity();
    update.setTeamRole(request.getRole());
    update.setNickname(request.getTeamNickname());
    teamMemberService.update(
        update,
        new LambdaQueryWrapper<TeamMemberEntity>()
            .eq(TeamMemberEntity::getTeamId, request.getTeamId())
            .eq(TeamMemberEntity::getUid, request.getUid()));

    if (CollectionUtils.isNotEmpty(request.getSpaceRoles())) {
      for (SpaceRoleDomain spaceRoleDomain : request.getSpaceRoles()) {
        String spaceRole = spaceRoleDomain.getSpaceRole();

        LambdaQueryWrapper<TeamSpaceMemberEntity> queryWrapper =
            new LambdaQueryWrapper<TeamSpaceMemberEntity>()
                .eq(TeamSpaceMemberEntity::getUid, request.getUid())
                .eq(TeamSpaceMemberEntity::getSpaceId, spaceRoleDomain.getSpaceId());

        TeamSpaceMemberEntity spaceMember = teamSpaceMemberService.getOne(queryWrapper);

        if (StringUtils.isBlank(spaceRole) && null != spaceMember) {
          teamSpaceMemberService.remove(queryWrapper);
        }

        if (StringUtils.isNotBlank(spaceRole)
            && null != spaceMember
            && !Objects.equals(spaceRole, spaceMember.getSpaceRole())) {
          TeamSpaceMemberEntity updateSpaceMember = new TeamSpaceMemberEntity();
          updateSpaceMember.setSpaceRole(spaceRole);
          teamSpaceMemberService.update(updateSpaceMember, queryWrapper);
        }

        if (StringUtils.isNotBlank(spaceRole) && null == spaceMember) {
          TeamSpaceMemberEntity insertSpaceMember = new TeamSpaceMemberEntity();
          insertSpaceMember.setSpaceId(spaceRoleDomain.getSpaceId());
          insertSpaceMember.setUid(request.getUid());
          insertSpaceMember.setSpaceRole(spaceRoleDomain.getSpaceRole());
          insertSpaceMember.setJoinTime(new Date());
          teamSpaceMemberService.save(insertSpaceMember);
        }
      }
    }
    return true;
  }

  @Override
  public Boolean removeTeamMemeber(String teamId, String uid, String currentUid) {
    TeamMemberEntity teamMemberEntity = teamMemberService.getByTeamIdAndUid(teamId, currentUid);

    if (!TeamRoleEnum.hasTeamManageAuth(teamMemberEntity.getTeamRole())) {
      throw new BusinessException("无权进行此操作");
    }

    TeamMemberEntity update = new TeamMemberEntity();
    update.setDelFlag(DelFlagEnum.DISABLE.getCode());

    teamMemberService.update(
        update,
        new LambdaQueryWrapper<TeamMemberEntity>()
            .eq(TeamMemberEntity::getTeamId, teamId)
            .eq(TeamMemberEntity::getUid, uid)
            .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));

    return true;
  }

  @Override
  public Boolean setSpaceInfo(
      String spaceId, String name, String desc, String logoUrl, String uid) {
    TeamMemberEntity memberEntity = teamMemberService.getBySpaceIdAndUid(spaceId, uid);
    if (!TeamRoleEnum.hasTeamManageAuth(memberEntity.getTeamRole())) {
      throw new BusinessException("无权进行此操作");
    }

    TeamSpaceEntity update = new TeamSpaceEntity();
    update.setId(spaceId);
    update.setName(name);
    update.setDesc(desc);
    update.setLogoUrl(logoUrl);
    this.updateById(update);

    return true;
  }

  @Override
  public Boolean setSpaceWatermark(String spaceId, Boolean showWatermark, String uid) {
    TeamMemberEntity memberEntity = teamMemberService.getBySpaceIdAndUid(spaceId, uid);
    if (!TeamRoleEnum.hasTeamManageAuth(memberEntity.getTeamRole())) {
      throw new BusinessException("无权进行此操作");
    }

    TeamSpaceEntity update = new TeamSpaceEntity();
    update.setId(spaceId);
    update.setShowWatermark(showWatermark);
    this.updateById(update);

    return true;
  }

  @Override
  public Boolean removeSpace(String spaceId, String name, String uid) {
    TeamMemberEntity memberEntity = teamMemberService.getBySpaceIdAndUid(spaceId, uid);
    if (!TeamRoleEnum.hasTeamManageAuth(memberEntity.getTeamRole())) {
      throw new BusinessException("无权进行此操作");
    }

    TeamSpaceEntity spaceEntity = this.getById(spaceId);
    if (!StringUtils.equals(spaceEntity.getName(), name)) {
      throw new BusinessException("空间名称验证错误");
    }

    this.removeById(spaceId);

    return true;
  }
}
