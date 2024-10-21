package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.enums.TeamRoleEnum;
import net.xbookmark.common.enums.TeamSpaceRoleEnum;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.core.TeamMemberService;
import net.xbookmark.core.TeamSpaceMemberService;
import net.xbookmark.core.TeamSpaceService;
import net.xbookmark.dao.mapper.TeamSpaceMemberMapper;
import net.xbookmark.dao.model.TeamMemberEntity;
import net.xbookmark.dao.model.TeamSpaceEntity;
import net.xbookmark.dao.model.TeamSpaceMemberEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangdingfei
 * @date 2023/7/19 21:47
 */
@Service
@Slf4j
public class TeamSpaceMemberServiceImpl
    extends ServiceImpl<TeamSpaceMemberMapper, TeamSpaceMemberEntity>
    implements TeamSpaceMemberService {

  @Autowired private TeamMemberService teamMemberService;

  @Autowired private TeamSpaceService teamSpaceService;

  @Override
  public void saveSpaceMember(String spaceId, String uid, String spaceRole) {
    TeamSpaceMemberEntity spaceMember =
        this.getOne(
            new LambdaQueryWrapper<TeamSpaceMemberEntity>()
                .eq(TeamSpaceMemberEntity::getSpaceId, spaceId)
                .eq(TeamSpaceMemberEntity::getUid, uid)
                .last("limit 1"));

    if (spaceMember != null && StringUtils.isBlank(spaceRole)) {
      this.removeById(spaceMember.getId());
      return;
    }

    if (spaceMember != null) {
      spaceMember.setSpaceRole(spaceRole);
      this.updateById(spaceMember);
    } else {
      spaceMember = new TeamSpaceMemberEntity();
      spaceMember.setSpaceId(spaceId);
      spaceMember.setUid(uid);
      spaceMember.setSpaceRole(spaceRole);
      spaceMember.setJoinTime(new Date());
      this.save(spaceMember);
    }
  }

  @Override
  public String getSpaceRole(String spaceId, String uid) {
    TeamSpaceMemberEntity spaceMember =
        this.getOne(
            new LambdaQueryWrapper<TeamSpaceMemberEntity>()
                .eq(TeamSpaceMemberEntity::getSpaceId, spaceId)
                .eq(TeamSpaceMemberEntity::getUid, uid));

    return spaceMember == null ? null : spaceMember.getSpaceRole();
  }

  @Override
  public void checkSpaceEditAuth(String spaceId, String uid) {
    TeamSpaceEntity teamSpace = teamSpaceService.getById(spaceId);

    TeamMemberEntity teamMember = teamMemberService.getByTeamIdAndUid(teamSpace.getTeamId(), uid);

    if (TeamRoleEnum.hasTeamManageAuth(teamMember.getTeamRole())) {
      return;
    }

    String spaceRole = this.getSpaceRole(spaceId, uid);

    if (TeamSpaceRoleEnum.hasSpaceEditorAuth(spaceRole)) {
      return;
    }

    throw new BusinessException("无权进行此操作");
  }
}
