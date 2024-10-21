package net.xbookmark.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.api.nav.web.response.TeamDirTreeResponse;
import net.xbookmark.api.service.TeamNavService;
import net.xbookmark.common.enums.DataTypeEnum;
import net.xbookmark.common.enums.DelFlagEnum;
import net.xbookmark.common.enums.TeamRoleEnum;
import net.xbookmark.common.enums.TeamSpaceRoleEnum;
import net.xbookmark.core.*;
import net.xbookmark.dao.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangdingfei
 * @date 2023/8/20 15:53
 */
@Service
@Slf4j
public class TeamNavServiceImpl implements TeamNavService {

  @Autowired private TeamSpaceService teamSpaceService;

  @Autowired private NavService navService;

  @Autowired private TeamSpaceMemberService teamSpaceMemberService;

  @Autowired private TeamMemberService teamMemberService;

  @Autowired private TeamService teamService;

  @Override
  public List<TeamDirTreeResponse> getDirTree(String uid, String teamId) {
    List<TeamSpaceEntity> spaceEntities =
        teamSpaceService.list(
            new LambdaQueryWrapper<TeamSpaceEntity>().eq(TeamSpaceEntity::getTeamId, teamId));

    TeamMemberEntity teamMemberEntity = teamMemberService.getByTeamIdAndUid(teamId, uid);

    List<TeamSpaceMemberEntity> memberEntityList =
        teamSpaceMemberService.list(
            new LambdaQueryWrapper<TeamSpaceMemberEntity>()
                .select(TeamSpaceMemberEntity::getSpaceId)
                .eq(TeamSpaceMemberEntity::getUid, uid)
                .in(
                    TeamSpaceMemberEntity::getSpaceRole,
                    Lists.newArrayList(
                        TeamSpaceRoleEnum.OWNER.name(), TeamSpaceRoleEnum.EDITOR.name())));

    List<String> hasAuthSpaceIds =
        memberEntityList.stream()
            .map(TeamSpaceMemberEntity::getSpaceId)
            .collect(Collectors.toList());

    List<TeamDirTreeResponse> responses = new ArrayList<>();

    for (TeamSpaceEntity spaceEntity : spaceEntities) {
      boolean hasAuth =
          TeamRoleEnum.hasTeamManageAuth(teamMemberEntity.getTeamRole())
              || hasAuthSpaceIds.contains(spaceEntity.getId());

      if (!hasAuth) {
        continue;
      }

      TeamDirTreeResponse treeResponse =
          TeamDirTreeResponse.builder()
              .dirId("root")
              .label(spaceEntity.getName())
              .value("root:" + spaceEntity.getId())
              .spaceId(spaceEntity.getId())
              .spaceType(spaceEntity.getSpaceType())
              .build();

      List<NavEntity> multi =
          navService.list(
              new LambdaQueryWrapper<NavEntity>()
                  .eq(NavEntity::getSpaceId, spaceEntity.getId())
                  .eq(NavEntity::getDataType, DataTypeEnum.DIR.getDataType())
                  .eq(NavEntity::getDelFlag, 0)
                  .orderByAsc(NavEntity::getSort));

      setChildren(treeResponse, multi);

      responses.add(treeResponse);
    }

    return responses;
  }

  @Override
  public List<TeamDirTreeResponse> getDirTree(String uid) {
    List<TeamMemberEntity> teamMemberEntities =
        teamMemberService.list(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .select(TeamMemberEntity::getTeamId, TeamMemberEntity::getTeamRole)
                .eq(TeamMemberEntity::getUid, uid)
                .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));

    List<String> teamIds =
        teamMemberEntities.stream().map(TeamMemberEntity::getTeamId).collect(Collectors.toList());

    Map<String, TeamMemberEntity> teamMemberEntityMap =
        teamMemberEntities.stream()
            .collect(Collectors.toMap(x -> x.getTeamId(), x -> x, (o1, o2) -> o1));

    List<TeamEntity> teamEntities =
        teamService.list(new LambdaQueryWrapper<TeamEntity>().in(TeamEntity::getId, teamIds));

    List<TeamSpaceEntity> spaceList =
        teamSpaceService.list(
            new LambdaQueryWrapper<TeamSpaceEntity>().in(TeamSpaceEntity::getTeamId, teamIds));

    List<TeamSpaceMemberEntity> memberEntityList =
        teamSpaceMemberService.list(
            new LambdaQueryWrapper<TeamSpaceMemberEntity>()
                .select(TeamSpaceMemberEntity::getSpaceId)
                .eq(TeamSpaceMemberEntity::getUid, uid)
                .in(
                    TeamSpaceMemberEntity::getSpaceRole,
                    Lists.newArrayList(
                        TeamSpaceRoleEnum.OWNER.name(), TeamSpaceRoleEnum.EDITOR.name())));

    List<String> hasAuthSpaceIds =
        memberEntityList.stream()
            .map(TeamSpaceMemberEntity::getSpaceId)
            .collect(Collectors.toList());

    Map<String, List<TeamSpaceEntity>> teamSpaceMap =
        spaceList.stream().collect(Collectors.groupingBy(x -> x.getTeamId()));

    List<TeamDirTreeResponse> responses = new ArrayList<>();

    for (TeamEntity teamEntity : teamEntities) {
      List<TeamSpaceEntity> spaceEntityList = teamSpaceMap.get(teamEntity.getId());
      if (CollectionUtils.isEmpty(spaceEntityList)) {
        continue;
      }
      TeamDirTreeResponse response = new TeamDirTreeResponse();
      response.setDirId(teamEntity.getId());
      response.setLabel(teamEntity.getOrgName());
      response.setDisable(true);
      response.setChildren(new ArrayList<>());

      TeamMemberEntity teamMemberEntity = teamMemberEntityMap.get(teamEntity.getId());

      for (TeamSpaceEntity spaceEntity : spaceEntityList) {
        boolean hasAuth =
            TeamRoleEnum.hasTeamManageAuth(teamMemberEntity.getTeamRole())
                || hasAuthSpaceIds.contains(spaceEntity.getId());

        if (!hasAuth) {
          continue;
        }

        TeamDirTreeResponse treeResponse =
            TeamDirTreeResponse.builder()
                .dirId("root")
                .label(spaceEntity.getName())
                .value("root:" + spaceEntity.getId())
                .spaceId(spaceEntity.getId())
                .spaceType(spaceEntity.getSpaceType())
                .build();

        List<NavEntity> multi =
            navService.list(
                new LambdaQueryWrapper<NavEntity>()
                    .eq(NavEntity::getSpaceId, spaceEntity.getId())
                    .eq(NavEntity::getDataType, DataTypeEnum.DIR.getDataType())
                    .eq(NavEntity::getDelFlag, 0)
                    .orderByAsc(NavEntity::getSort));

        setChildren(treeResponse, multi);

        response.getChildren().add(treeResponse);
      }

      if (!response.getChildren().isEmpty()) {
        responses.add(response);
      }
    }

    return responses;
  }

  private void setChildren(TeamDirTreeResponse parent, List<NavEntity> multi) {
    final List<NavEntity> childs =
        multi.stream()
            .filter(e -> e.getPid().equals(parent.getDirId()))
            .collect(Collectors.toList());
    if (CollectionUtils.isEmpty(childs)) {
      return;
    }

    List<TeamDirTreeResponse> children = new ArrayList<>();

    for (NavEntity child : childs) {
      TeamDirTreeResponse childRes =
          TeamDirTreeResponse.builder()
              .dirId(child.getId())
              .label(child.getName())
              .value(child.getId())
              .build();
      setChildren(childRes, multi);

      children.add(childRes);
    }

    parent.setChildren(children);
  }
}
