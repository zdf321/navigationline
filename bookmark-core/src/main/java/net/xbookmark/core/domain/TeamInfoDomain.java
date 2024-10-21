package net.xbookmark.core.domain;

import com.google.common.base.Objects;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangdingfei
 * @date 2023/7/18 15:22
 */
@Data
public class TeamInfoDomain {

  private String teamId;

  private String teamType;

  private String name;

  private String vipName;

  private String vipType;

  private String teamRole;

  private String creator;

  private Date createTime;

  private Date expireTime;

  private Date joinTime;

  private Long teamMemberCount;

  private String scaleName;

  private String businessType;

  private String teamNickname;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TeamInfoDomain domain = (TeamInfoDomain) o;
    return Objects.equal(teamId, domain.teamId) && Objects.equal(teamType, domain.teamType);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(teamId, teamType);
  }
}
