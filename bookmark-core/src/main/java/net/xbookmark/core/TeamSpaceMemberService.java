package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.dao.model.TeamSpaceMemberEntity;

/**
 * @author zhangdingfei
 * @date 2023/7/19 21:36
 */
public interface TeamSpaceMemberService extends IService<TeamSpaceMemberEntity> {

  /**
   * 添加空间成员
   *
   * @param spaceId
   * @param uid
   * @param spaceRole
   */
  void saveSpaceMember(String spaceId, String uid, String spaceRole);

  String getSpaceRole(String spaceId, String uid);

  /**
   * 检查是否有空间的编辑权限
   *
   * @param spaceId
   * @param uid
   */
  void checkSpaceEditAuth(String spaceId, String uid);
}
