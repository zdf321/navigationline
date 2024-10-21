package net.xbookmark.core.domain;

import lombok.Data;

/**
 * @author zhangdingfei
 * @date 2023/9/4 13:43
 */
@Data
public class TeamInviteInfoDomain {

  /** 邀请链接 */
  private String inviteLink;

  /** 是否开启团队邀请链接 0-关闭 1-开启 */
  private Integer inviteLinkOpen;

  /** 团队邀请链接有效期（天）-1表示永久有效 */
  private Integer inviteLinkDays;

  /** 链接是否有效 */
  private Boolean inviteLinkValid;
}
