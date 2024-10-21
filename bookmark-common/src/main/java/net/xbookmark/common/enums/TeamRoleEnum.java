package net.xbookmark.common.enums;

/**
 * 团队角色
 *
 * @author zhangdingfei
 * @date 2023/7/17 21:59
 */
public enum TeamRoleEnum {
  /** 团队创建者 */
  OWNER,
  /** 团队管理员 */
  ADMIN,
  /** 普通成员 */
  NORMAL;

  /**
   * 是否拥有团队的管理权限
   *
   * @param teamRole
   * @return
   */
  public static boolean hasTeamManageAuth(String teamRole) {
    if (OWNER.name().equals(teamRole) || ADMIN.name().equals(teamRole)) {
      return true;
    }
    return false;
  }
}
