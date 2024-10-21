package net.xbookmark.core.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.enums.*;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.util.UserContext;
import net.xbookmark.config.NavProperties;
import net.xbookmark.core.*;
import net.xbookmark.core.domain.InviteTeamInfoDomain;
import net.xbookmark.core.domain.TeamInfoDomain;
import net.xbookmark.core.domain.TeamInviteInfoDomain;
import net.xbookmark.core.domain.TeamSpaceInfoDomain;
import net.xbookmark.dao.mapper.TeamMapper;
import net.xbookmark.dao.model.AccountEntity;
import net.xbookmark.dao.model.TeamEntity;
import net.xbookmark.dao.model.TeamInviteInfoEntity;
import net.xbookmark.dao.model.TeamMemberEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangdingfei
 * @date 2023/7/18 14:33
 */
@Service
@Slf4j
public class TeamServiceImpl extends ServiceImpl<TeamMapper, TeamEntity> implements TeamService {

  @Autowired private TeamSpaceService teamSpaceService;

  @Autowired private TeamMemberService teamMemberService;

  @Autowired private TeamSpaceMemberService teamSpaceMemberService;

  @Autowired private TeamDeptService teamDeptService;

  @Autowired private NavProperties navProperties;

  @Autowired private TeamInviteInfoService teamInviteInfoService;

  @Autowired private AccountService accountService;

  @Override
  public void addPersonalTeam(String uid) {
    TeamEntity teamEntity = this.baseMapper.getPersonalTeamByUid(uid);
    if (null != teamEntity) {
      return;
    }
    teamEntity = new TeamEntity();
    teamEntity.setTeamType(TeamTypeEnum.PERSONAL.name());
    teamEntity.setOrgName("个人空间");
    teamEntity.setUid(uid);
    teamEntity.setVipType(VIPEnum.P_FREE.name());
    teamEntity.setMaxPeople(1);

    this.save(teamEntity);

    AccountEntity account = accountService.getById(uid);

    teamMemberService.saveTeamMember(
        teamEntity.getId(),
        uid,
        account.getNickname(),
        JoinWayEnum.DEFAULT,
        null,
        TeamRoleEnum.OWNER.name(),
        null);

    teamSpaceService.addTeamSpace(
        teamEntity.getId(),
        uid,
        "个人空间",
        "个人空间",
        navProperties.getSystem().getFileMaxCountVisitor(),
        TeamSpaceTypeEnum.PERSONAL,
        false);
  }

  @Override
  public void addTeam(
      String uid, String orgName, String nickname, String scale, String businessType) {
    // 有效的试用版同时只能有一个
    long count =
        this.count(
            new LambdaQueryWrapper<TeamEntity>()
                .eq(TeamEntity::getUid, uid)
                .eq(TeamEntity::getVipType, VIPEnum.T_TRIAL.name())
                .gt(TeamEntity::getExpireTime, new Date()));

    TeamEntity teamEntity = new TeamEntity();
    teamEntity.setTeamType(TeamTypeEnum.TEAM.name());
    teamEntity.setOrgName(orgName);
    teamEntity.setUid(uid);
    teamEntity.setScale(scale);
    teamEntity.setBusinessType(businessType);
    teamEntity.setVipType(count == 0 ? VIPEnum.T_TRIAL.name() : VIPEnum.T_EXPIRED.name());
    try {
      teamEntity.setExpireTime(DateUtils.parseDate("2199-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss"));
    } catch (ParseException e) {
    }
    teamEntity.setMaxPeople(navProperties.getSystem().getTeamTrialPeople());

    this.save(teamEntity);

    teamMemberService.saveTeamMember(
        teamEntity.getId(),
        uid,
        nickname,
        JoinWayEnum.DEFAULT,
        null,
        TeamRoleEnum.OWNER.name(),
        null);

    String mineSpaceId =
        teamSpaceService.addTeamSpace(
            teamEntity.getId(), uid, "我的空间", "我的空间", null, TeamSpaceTypeEnum.MINE, false);
    teamSpaceMemberService.saveSpaceMember(mineSpaceId, uid, TeamSpaceRoleEnum.OWNER.name());

    String publicSpaceId =
        teamSpaceService.addTeamSpace(
            teamEntity.getId(), uid, "公共空间", "公共空间", null, TeamSpaceTypeEnum.PUBLIC, false);
    teamSpaceMemberService.saveSpaceMember(publicSpaceId, uid, TeamSpaceRoleEnum.OWNER.name());

    // 创建默认部门
    teamDeptService.addTeamDept(teamEntity.getId(), teamEntity.getOrgName(), "root");
  }

  @Override
  public List<TeamInfoDomain> getTeamList(String uid) {
    List<TeamMemberEntity> teamMemberEntities =
        teamMemberService.list(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .eq(TeamMemberEntity::getUid, uid)
                .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));

    List<String> teamIdList =
        teamMemberEntities.stream().map(TeamMemberEntity::getTeamId).collect(Collectors.toList());

    Map<String, TeamMemberEntity> teamMemberEntityMap =
        teamMemberEntities.stream().collect(Collectors.toMap(x -> x.getTeamId(), x -> x));

    if (CollectionUtils.isEmpty(teamIdList)) {
      return Collections.emptyList();
    }

    List<TeamEntity> teamEntityList =
        this.list(new LambdaQueryWrapper<TeamEntity>().in(TeamEntity::getId, teamIdList));

    List<TeamInfoDomain> domains = new ArrayList<>();

    for (TeamEntity teamEntity : teamEntityList) {
      TeamInfoDomain domain = new TeamInfoDomain();
      domain.setTeamId(teamEntity.getId());
      domain.setTeamType(teamEntity.getTeamType());
      domain.setName(teamEntity.getOrgName());
      domain.setVipType(teamEntity.getVipType());

      TeamMemberEntity teamMemberEntity = teamMemberEntityMap.get(teamEntity.getId());
      if (null != teamMemberEntity) {
        domain.setTeamRole(teamMemberEntity.getTeamRole());
      }

      VIPEnum vipEnum = VIPEnum.getByType(teamEntity.getVipType());
      if (null != vipEnum) {
        domain.setVipName(vipEnum.getName());
      }
      domains.add(domain);
    }

    // 将个人空间放在首位
    LinkedList<TeamInfoDomain> linkedList = new LinkedList<>();
    for (TeamInfoDomain domain : domains) {
      if (TeamTypeEnum.PERSONAL.name().equals(domain.getTeamType())) {
        linkedList.addFirst(domain);
      } else {
        linkedList.add(domain);
      }
    }

    return linkedList;
  }

  @Override
  public TeamInfoDomain getTeamInfo(String uid, String teamId) {
    if (!validTeamId(uid, teamId)) {
      return null;
    }

    TeamInfoDomain domain = new TeamInfoDomain();

    TeamEntity teamEntity = this.getById(teamId);
    domain.setTeamId(teamEntity.getId());
    domain.setTeamType(teamEntity.getTeamType());
    domain.setName(teamEntity.getOrgName());
    domain.setVipType(teamEntity.getVipType());
    domain.setCreateTime(teamEntity.getCreateTime());
    domain.setExpireTime(teamEntity.getExpireTime());
    domain.setBusinessType(teamEntity.getBusinessType());

    TeamScaleEnum teamScaleEnum = TeamScaleEnum.getByCode(teamEntity.getScale());
    domain.setScaleName(teamScaleEnum == null ? null : teamScaleEnum.getName());

    VIPEnum vipEnum = VIPEnum.getByType(teamEntity.getVipType());
    if (null != vipEnum) {
      domain.setVipName(vipEnum.getName());
    }

    AccountEntity createAccount = accountService.getById(teamEntity.getUid());
    domain.setCreator(createAccount.getNickname());

    TeamMemberEntity teamMember =
        teamMemberService.getOne(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .eq(TeamMemberEntity::getTeamId, teamId)
                .eq(TeamMemberEntity::getUid, uid)
                .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));
    domain.setTeamRole(teamMember.getTeamRole());
    domain.setTeamNickname(teamMember.getNickname());
    domain.setJoinTime(teamMember.getJoinTime());

    long teamMemberCount =
        teamMemberService.count(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .eq(TeamMemberEntity::getTeamId, teamId)
                .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));
    domain.setTeamMemberCount(teamMemberCount);

    return domain;
  }

  @Override
  public TeamInfoDomain getPersonalTeamInfo(String uid) {

    TeamInfoDomain domain = new TeamInfoDomain();

    TeamEntity teamEntity =
        this.getOne(
            new LambdaQueryWrapper<TeamEntity>()
                .eq(TeamEntity::getUid, uid)
                .eq(TeamEntity::getTeamType, TeamTypeEnum.PERSONAL.name()));

    domain.setTeamId(teamEntity.getId());
    domain.setTeamType(teamEntity.getTeamType());
    domain.setName(teamEntity.getOrgName());
    domain.setVipType(teamEntity.getVipType());
    domain.setCreateTime(teamEntity.getCreateTime());
    domain.setExpireTime(teamEntity.getExpireTime());
    domain.setBusinessType(teamEntity.getBusinessType());

    TeamScaleEnum teamScaleEnum = TeamScaleEnum.getByCode(teamEntity.getScale());
    domain.setScaleName(teamScaleEnum == null ? null : teamScaleEnum.getName());

    VIPEnum vipEnum = VIPEnum.getByType(teamEntity.getVipType());
    if (null != vipEnum) {
      domain.setVipName(vipEnum.getName());
    }

    AccountEntity createAccount = accountService.getById(teamEntity.getUid());
    domain.setCreator(createAccount.getNickname());

    TeamMemberEntity teamMember =
        teamMemberService.getOne(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .eq(TeamMemberEntity::getTeamId, teamEntity.getId())
                .eq(TeamMemberEntity::getUid, uid)
                .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));
    domain.setTeamRole(teamMember.getTeamRole());
    domain.setTeamNickname(teamMember.getNickname());
    domain.setJoinTime(teamMember.getJoinTime());

    domain.setTeamMemberCount(1L);

    return domain;
  }

  @Override
  public boolean validTeamId(String uid, String teamId) {
    long count =
        teamMemberService.count(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .eq(TeamMemberEntity::getUid, uid)
                .eq(TeamMemberEntity::getTeamId, teamId)
                .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));
    return count > 0;
  }

  @Override
  public String setInviteLink(String teamId, Boolean isOpen, Integer validDays, String uid) {
    // 校验权限
    TeamMemberEntity teamMemberEntity =
        teamMemberService.getOne(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .eq(TeamMemberEntity::getTeamId, teamId)
                .eq(TeamMemberEntity::getUid, uid)
                .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));

    if (null == teamMemberEntity) {
      throw new BusinessException("您没有权限进行此操作");
    }

    boolean hasManageAuth = TeamRoleEnum.hasTeamManageAuth(teamMemberEntity.getTeamRole());
    if (!hasManageAuth) {
      throw new BusinessException("您没有权限进行此操作");
    }

    TeamInviteInfoEntity teamInviteInfoEntity =
        teamInviteInfoService.getOne(
            new LambdaQueryWrapper<TeamInviteInfoEntity>()
                .eq(TeamInviteInfoEntity::getTeamId, teamId)
                .eq(TeamInviteInfoEntity::getUid, uid));

    if (null == teamInviteInfoEntity) {
      teamInviteInfoEntity = new TeamInviteInfoEntity();
      teamInviteInfoEntity.setTeamId(teamId);
      teamInviteInfoEntity.setUid(uid);
    }

    if (isOpen) {
      teamInviteInfoEntity.setInviteLinkOpen(1);
      teamInviteInfoEntity.setInviteLinkDays(validDays);
      teamInviteInfoEntity.setInviteLinkStartTime(new Date());
      teamInviteInfoEntity.setInviteId(UUID.fastUUID().toString(true));
    } else {
      teamInviteInfoEntity.setInviteLinkOpen(0);
    }

    teamInviteInfoService.saveOrUpdate(teamInviteInfoEntity);

    return teamInviteInfoEntity.getInviteId();
  }

  @Override
  public TeamInviteInfoDomain getInviteInfo(String teamId, String uid) {
    TeamInviteInfoEntity teamInviteInfoEntity =
        teamInviteInfoService.getOne(
            new LambdaQueryWrapper<TeamInviteInfoEntity>()
                .eq(TeamInviteInfoEntity::getTeamId, teamId)
                .eq(TeamInviteInfoEntity::getUid, uid));

    TeamInviteInfoDomain domain = new TeamInviteInfoDomain();

    if (null != teamInviteInfoEntity && teamInviteInfoEntity.getInviteLinkOpen() == 1) {
      domain.setInviteLink(teamInviteInfoEntity.getInviteId());
      domain.setInviteLinkOpen(teamInviteInfoEntity.getInviteLinkOpen());
      domain.setInviteLinkDays(teamInviteInfoEntity.getInviteLinkDays());

      if (teamInviteInfoEntity.getInviteLinkDays() == -1) {
        domain.setInviteLinkValid(true);
      } else {
        Date finalTime =
            DateUtils.addDays(
                teamInviteInfoEntity.getInviteLinkStartTime(),
                teamInviteInfoEntity.getInviteLinkDays());
        domain.setInviteLinkValid(finalTime.after(new Date()));
      }
    } else {
      domain.setInviteLinkOpen(0);
    }

    return domain;
  }

  @Override
  public InviteTeamInfoDomain getTeamInfoByInviteId(String inviteId) {
    TeamInviteInfoEntity teamInviteInfo =
        teamInviteInfoService.getOne(
            new LambdaQueryWrapper<TeamInviteInfoEntity>()
                .eq(TeamInviteInfoEntity::getInviteId, inviteId));
    if (null == teamInviteInfo) {
      log.error("邀请链接id：{}不存在", inviteId);
      return null;
    }

    if (teamInviteInfo.getInviteLinkOpen() == 0) {
      log.error("邀请链接id：{}是关闭状态", inviteId);
      return null;
    }

    if (!Objects.equals(teamInviteInfo.getInviteLinkDays(), -1)) {
      Date finalTime =
          DateUtils.addDays(
              teamInviteInfo.getInviteLinkStartTime(), teamInviteInfo.getInviteLinkDays());
      if (finalTime.before(new Date())) {
        log.error("邀请链接id：{}已过期", inviteId);
        return null;
      }
    }

    TeamEntity teamEntity = this.getById(teamInviteInfo.getTeamId());
    if (null == teamEntity) {
      log.error("邀请链接id：{}对应的团队已被删除", inviteId);
      return null;
    }

    TeamMemberEntity teamMemberEntity =
        teamMemberService.getOne(
            new LambdaQueryWrapper<TeamMemberEntity>()
                .eq(TeamMemberEntity::getTeamId, teamEntity.getId())
                .eq(TeamMemberEntity::getUid, teamInviteInfo.getUid())
                .eq(TeamMemberEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()));
    if (null == teamMemberEntity) {
      log.error("邀请链接id：{}，生成时的用户已不在团队", inviteId);
      return null;
    }

    InviteTeamInfoDomain teamInfoDomain = new InviteTeamInfoDomain();
    teamInfoDomain.setTeamId(teamEntity.getId());
    teamInfoDomain.setTeamName(teamEntity.getOrgName());
    teamInfoDomain.setTeamType(teamEntity.getTeamType());
    teamInfoDomain.setNickname(teamMemberEntity.getNickname());

    return teamInfoDomain;
  }

  @Override
  public Boolean addTeamMemeberByInviteId(String inviteId, String nickname, String uid) {
    TeamInviteInfoEntity teamInviteInfo =
        teamInviteInfoService.getOne(
            new LambdaQueryWrapper<TeamInviteInfoEntity>()
                .eq(TeamInviteInfoEntity::getInviteId, inviteId));
    if (null == teamInviteInfo) {
      throw new BusinessException("数据不存在");
    }

    // 添加团队成员
    teamMemberService.saveTeamMember(
        teamInviteInfo.getTeamId(),
        uid,
        nickname,
        JoinWayEnum.INVITE_LINK,
        teamInviteInfo.getUid(),
        TeamRoleEnum.NORMAL.name(),
        null);

    TeamSpaceInfoDomain publicSpace = teamSpaceService.getPublicSpace(teamInviteInfo.getTeamId());

    // 将公共空间给用户
    teamSpaceMemberService.saveSpaceMember(
        publicSpace.getSpaceId(), uid, TeamSpaceRoleEnum.EDITOR.name());

    // 创建自己的空间
    teamSpaceService.addTeamSpace(
        teamInviteInfo.getTeamId(), uid, "我的空间", "我的空间", null, TeamSpaceTypeEnum.MINE, false);

    return true;
  }

  @Override
  public Boolean setTeamInfo(String teamId, String teamName, String teamNickname, String ownerUid) {
    TeamEntity teamEntity = this.getById(teamId);

    // 修改团队名称
    if (StringUtils.isNotBlank(teamName) && !Objects.equals(teamEntity.getOrgName(), teamName)) {
      teamEntity.setOrgName(teamName);
      this.updateById(teamEntity);
    }

    // 移交团队
    if (StringUtils.isNotBlank(ownerUid)) {
      TeamMemberEntity ownerMember =
          teamMemberService.getOne(
              new LambdaQueryWrapper<TeamMemberEntity>()
                  .eq(TeamMemberEntity::getTeamId, teamId)
                  .eq(TeamMemberEntity::getTeamRole, TeamRoleEnum.OWNER.name()));

      if (!Objects.equals(ownerUid, ownerMember.getUid())) {
        // 移交团队后，原团队所有者角色变更为普通角色
        ownerMember.setTeamRole(TeamRoleEnum.NORMAL.name());
        teamMemberService.updateById(ownerMember);

        TeamMemberEntity teamMemberEntity = teamMemberService.getByTeamIdAndUid(teamId, ownerUid);
        teamMemberEntity.setTeamRole(TeamRoleEnum.OWNER.name());
        teamMemberService.updateById(teamMemberEntity);
      }
    }

    // 修改我在团队中的昵称
    TeamMemberEntity member = teamMemberService.getByTeamIdAndUid(teamId, UserContext.getUid());
    if (StringUtils.isNotBlank(teamNickname)
        && !Objects.equals(member.getNickname(), teamNickname)) {
      member.setNickname(teamNickname);
      teamMemberService.updateById(member);
    }
    return true;
  }

  @Override
  public Boolean removeTeam(String teamId, String uid) {
    TeamMemberEntity teamMember = teamMemberService.getByTeamIdAndUid(teamId, uid);
    if (teamMember == null
        || !Objects.equals(teamMember.getTeamRole(), TeamRoleEnum.OWNER.name())) {
      throw new BusinessException("无权进行此操作");
    }

    this.removeById(teamId);

    return true;
  }
}
