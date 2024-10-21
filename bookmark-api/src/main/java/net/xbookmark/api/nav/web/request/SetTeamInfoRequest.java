package net.xbookmark.api.nav.web.request;

import lombok.Data;

/**
 * @author zhangdingfei
 * @date 2023/9/19 23:06
 */
@Data
public class SetTeamInfoRequest {
  /** 团队id */
  private String teamId;

  /** 团队名称 */
  private String teamName;

  /** 当前账号的团队昵称 */
  private String teamNickname;

  /** 团队所有者 */
  private String ownerUid;
}
