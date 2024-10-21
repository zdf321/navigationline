package net.xbookmark.common.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangdingfei
 * @date 2023/9/11 22:09
 */
@Data
public class TeamMemberDomain {

  private String uid;

  private String nickname;

  private String teamNickname;

  private Date joinTime;

  private String role;
}
