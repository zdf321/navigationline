package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.domain.TeamMemberDomain;
import net.xbookmark.common.domain.TeamSpaceMemberDomain;
import net.xbookmark.common.enums.DelFlagEnum;
import net.xbookmark.common.enums.JoinWayEnum;
import net.xbookmark.common.enums.TeamRoleEnum;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.core.TeamMemberService;
import net.xbookmark.dao.mapper.TeamMemberMapper;
import net.xbookmark.dao.model.TeamMemberEntity;
import net.xbookmark.dao.model.TeamSpaceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangdingfei
 * @date 2023/7/19 21:39
 */
@Service
@Slf4j
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMemberEntity>
    implements TeamMemberService {

  @Autowired private TeamSpaceServiceImpl teamSpaceService;

  @Override
  public void saveTeamMember(
      String teamId,
      String uid,
      String nickname,
      JoinWayEnum joinWay,
      String inviteUid,
      String role,
      String deptId) {

    TeamMemberEntity member =
        this.getOne(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .eq(TeamMemberEntity::getTeamId, teamId)
                .eq(TeamMemberEntity::getUid, uid)
                .last("limit 1"));

    if (member != null) {
      member.setNickname(nickname);
      member.setTeamRole(role);
      member.setDeptId(deptId);
      this.updateById(member);
    } else {
      member = new TeamMemberEntity();
      member.setTeamId(teamId);
      member.setUid(uid);
      member.setNickname(nickname);
      member.setJoinTime(new Date());
      member.setJoinWay(joinWay.getCode());
      member.setInviteUid(inviteUid);
      member.setTeamRole(role);
      member.setDeptId(deptId);
      this.save(member);
    }
  }

  @Override
  public IPage<TeamSpaceMemberDomain> getSpaceMemberPage(
      IPage<TeamSpaceMemberDomain> page, String spaceId, String name) {
    TeamSpaceEntity teamSpace = teamSpaceService.getById(spaceId);
    if (null == teamSpace) {
      return new Page<>();
    }

    return this.baseMapper.getSpaceMemberPage(page, teamSpace.getTeamId(), spaceId, name);
  }

  @Override
  public TeamSpaceMemberDomain getSpaceMember(String spaceId, String uid) {
    TeamSpaceEntity teamSpace = teamSpaceService.getById(spaceId);
    if (null == teamSpace) {
      return null;
    }
    return this.baseMapper.getSpaceMember(teamSpace.getTeamId(), spaceId, uid);
  }

  @Override
  public IPage<TeamMemberDomain> getTeamMemberPage(
      IPage<TeamMemberDomain> page, String teamId, String name) {
    return this.baseMapper.getTeamMemberPage(page, teamId, name);
  }

  @Override
  public TeamMemberEntity getByTeamIdAndUid(String teamId, String uid) {
    TeamMemberEntity teamMember =
        this.getOne(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .eq(TeamMemberEntity::getTeamId, teamId)
                .eq(TeamMemberEntity::getUid, uid)
                .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));

    return teamMember;
  }

  @Override
  public TeamMemberEntity getBySpaceIdAndUid(String spaceId, String uid) {
    TeamSpaceEntity teamSpaceEntity = teamSpaceService.getById(spaceId);
    return getByTeamIdAndUid(teamSpaceEntity.getTeamId(), uid);
  }

  @Override
  public void checkTeamManageAuth(String teamId, String uid) {
    TeamMemberEntity teamMember = getByTeamIdAndUid(teamId, uid);
    if (null != teamMember) {
      String teamRole = teamMember.getTeamRole();
      if (TeamRoleEnum.hasTeamManageAuth(teamRole)) {
        return;
      }
    }

    throw new BusinessException("无权进行此操作");
  }
}
