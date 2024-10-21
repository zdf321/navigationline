package net.xbookmark.common.enums;

/**
 * 团队空间角色
 *
 * @author zhangdingfei
 * @date 2023/7/17 21:59
 */
public enum TeamSpaceRoleEnum {
  /** 拥有者 */
  OWNER,
  /** 可编辑：可新建、编辑、下载、移动 */
  EDITOR,
  /** 可查看：仅支持在线浏览 */
  READER;

  /**
   * 是否拥有空间的管理权限
   *
   * @param spaceRole
   * @return
   */
  public static boolean hasSpaceManageAuth(String spaceRole) {
    return OWNER.name().equals(spaceRole);
  }

  /**
   * 是否有编辑空间内容的权限
   *
   * @param spaceRole
   * @return
   */
  public static boolean hasSpaceEditorAuth(String spaceRole) {
    if (OWNER.name().equals(spaceRole) || EDITOR.name().equals(spaceRole)) {
      return true;
    }

    return false;
  }
}
