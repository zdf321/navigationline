package net.xbookmark.core.domain;

import lombok.Data;

/**
 * @author zhangdingfei
 * @date 2023/9/4 22:55
 */
@Data
public class InviteTeamInfoDomain {

  private String teamId;

  private String teamName;

  private String teamType;

  private String nickname;
}
