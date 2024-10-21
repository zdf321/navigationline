package net.xbookmark.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangdingfei
 * @date 2023/9/13 23:07
 */
@Data
public class TeamAndSpaceAuthDomain {

  private String uid;

  private String teamRole;

  private String spaceRole;

  private Boolean hasAuthToManageSpace = false;

  private Boolean hasAuthToEditSpace = false;

  private Boolean hasAuthToManageTeam = false;
}
