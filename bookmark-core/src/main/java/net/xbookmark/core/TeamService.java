package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.core.domain.InviteTeamInfoDomain;
import net.xbookmark.core.domain.TeamInfoDomain;
import net.xbookmark.core.domain.TeamInviteInfoDomain;
import net.xbookmark.dao.model.TeamEntity;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/7/18 14:30
 */
public interface TeamService extends IService<TeamEntity> {

  /**
   * 添加个人团队
   *
   * @param uid
   */
  void addPersonalTeam(String uid);

  /**
   * 添加试用版团队
   *
   * @param uid
   * @param orgName
   * @param nickname
   * @param scale
   * @param businessType
   */
  void addTeam(String uid, String orgName, String nickname, String scale, String businessType);

  /**
   * 获取指定用户的团队列表
   *
   * @param uid
   * @return
   */
  List<TeamInfoDomain> getTeamList(String uid);

  /**
   * 获取团队信息
   *
   * @param uid
   * @param teamId
   * @return
   */
  TeamInfoDomain getTeamInfo(String uid, String teamId);

  /**
   * 获取个人空间团队信息
   *
   * @param uid
   * @return
   */
  TeamInfoDomain getPersonalTeamInfo(String uid);

  /**
   * 验证指定用户是否属于这个team
   *
   * @param uid
   * @param teamId
   * @return
   */
  boolean validTeamId(String uid, String teamId);

  /**
   * 设置团队分享链接
   *
   * @param teamId
   * @param isOpen
   * @param validDays
   * @param uid
   * @return
   */
  String setInviteLink(String teamId, Boolean isOpen, Integer validDays, String uid);

  /**
   * 获取团队邀请链接信息
   *
   * @param teamId
   * @param uid
   * @return
   */
  TeamInviteInfoDomain getInviteInfo(String teamId, String uid);

  /**
   * 根据邀请链接id获取团队信息
   *
   * @param inviteId
   * @return
   */
  InviteTeamInfoDomain getTeamInfoByInviteId(String inviteId);

  /**
   * 根据邀请链接id添加团队成员
   *
   * @param inviteId
   * @param uid
   * @return
   */
  Boolean addTeamMemeberByInviteId(String inviteId, String nickname, String uid);

  Boolean setTeamInfo(String teamId, String teamName, String teamNickname, String ownerUid);

  Boolean removeTeam(String teamId, String uid);
}
