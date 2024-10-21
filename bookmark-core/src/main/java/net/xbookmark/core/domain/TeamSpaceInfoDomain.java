package net.xbookmark.core.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhangdingfei
 * @date 2023/7/23 15:56
 */
@Data
@Builder
public class TeamSpaceInfoDomain {

  private String spaceId;

  private String name;

  private String desc;

  private String logoUrl;

  private Boolean showWatermark;

  /** 空间类型 */
  private String spaceType;

  /** 空间角色 */
  private String spaceRole;
}
