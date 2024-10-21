package net.xbookmark.core;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.common.domain.TeamMemberDomain;
import net.xbookmark.common.domain.TeamSpaceMemberDomain;
import net.xbookmark.common.enums.JoinWayEnum;
import net.xbookmark.dao.model.TeamMemberEntity;

/**
 * @author zhangdingfei
 * @date 2023/7/19 21:36
 */
public interface TeamMemberService extends IService<TeamMemberEntity> {

  /**
   * 添加团队成员
   *
   * @param teamId
   * @param uid
   * @param nickname
   * @param role
   * @param deptId
   */
  void saveTeamMember(
      String teamId,
      String uid,
      String nickname,
      JoinWayEnum joinWay,
      String inviteUid,
      String role,
      String deptId);

  IPage<TeamSpaceMemberDomain> getSpaceMemberPage(
      IPage<TeamSpaceMemberDomain> page, String spaceId, String name);

  TeamSpaceMemberDomain getSpaceMember(String spaceId, String uid);

  IPage<TeamMemberDomain> getTeamMemberPage(
      IPage<TeamMemberDomain> page, String teamId, String name);

  TeamMemberEntity getByTeamIdAndUid(String teamId, String uid);

  TeamMemberEntity getBySpaceIdAndUid(String spaceId, String uid);

  void checkTeamManageAuth(String teamId, String uid);
}
