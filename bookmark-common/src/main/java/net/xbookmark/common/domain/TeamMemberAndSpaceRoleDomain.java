package net.xbookmark.common.domain;

import lombok.Data;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/9/11 22:09
 */
@Data
public class TeamMemberAndSpaceRoleDomain {

  private String teamId;

  private String uid;

  private String teamNickname;

  private String role;

  private List<SpaceRoleDomain> spaceRoles;
}
