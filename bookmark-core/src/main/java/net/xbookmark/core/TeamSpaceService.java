package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.common.domain.TeamAndSpaceAuthDomain;
import net.xbookmark.common.domain.TeamMemberAndSpaceRoleDomain;
import net.xbookmark.common.domain.TeamMemberDomain;
import net.xbookmark.common.domain.TeamSpaceMemberDomain;
import net.xbookmark.common.enums.TeamSpaceTypeEnum;
import net.xbookmark.common.response.TableDataInfo;
import net.xbookmark.core.domain.TeamSpaceInfoDomain;
import net.xbookmark.dao.model.TeamSpaceEntity;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/7/18 14:48
 */
public interface TeamSpaceService extends IService<TeamSpaceEntity> {

  String addTeamSpace(
      String teamId,
      String uid,
      String name,
      String desc,
      Integer maxFileCount,
      TeamSpaceTypeEnum teamSpaceTypeEnum,
      Boolean showWatermark);

  /**
   * 获取指定团队的空间，不包含isMine=true
   *
   * @param teamId
   * @param uid
   * @return
   */
  List<TeamSpaceInfoDomain> getTeamSpaceList(String teamId, String uid);

  /**
   * 获取指定团队的团队空间
   *
   * @param teamId
   * @param uid
   * @return
   */
  List<TeamSpaceInfoDomain> getTeamSpaceList(String teamId);

  /**
   * 获取状态是我的空间
   *
   * @param teamId
   * @param uid
   * @return
   */
  TeamSpaceInfoDomain getMineSpace(String teamId, String uid);

  /**
   * 获取个人空间
   *
   * @param teamId
   * @param uid
   * @return
   */
  TeamSpaceInfoDomain getPersonalSpace(String teamId, String uid);

  /**
   * 获取公共空间
   *
   * @param teamId
   * @return
   */
  TeamSpaceInfoDomain getPublicSpace(String teamId);

  TableDataInfo<TeamSpaceMemberDomain> getSpaceMemberPage(
      Integer pageNo, Integer pageSize, String spaceId, String name);

  TeamSpaceMemberDomain setSpaceMemberRole(
      String spaceId, String uid, String role, String currentUid);

  TeamAndSpaceAuthDomain getMyTeamAndSpaceAuth(String teamId, String spaceId, String uid);

  TableDataInfo<TeamMemberDomain> getTeamMemberPage(
      Integer pageNo, Integer pageSize, String teamId, String name);

  List<TeamMemberDomain> getTeamMemberList(String teamId, String name);

  TeamMemberAndSpaceRoleDomain getTeamMemberAndSpaceRoleInfo(String teamId, String uid);

  Boolean setTeamMemberAndSpaceRoleInfo(TeamMemberAndSpaceRoleDomain request, String currentUid);

  Boolean removeTeamMemeber(String teamId, String uid, String currentUid);

  Boolean setSpaceInfo(String spaceId, String name, String desc, String logoUrl, String uid);

  Boolean setSpaceWatermark(String spaceId, Boolean showWatermark, String uid);

  Boolean removeSpace(String spaceId, String name, String uid);
}
