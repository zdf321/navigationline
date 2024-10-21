package net.xbookmark.core.domain;

import lombok.Data;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/10/11 21:10
 */
@Data
public class TeamPublishDataDomain {

  private String teamId;

  private String teamName;

  /** 团队空间内容 */
  private List<TeamPublishRowDomain> rowList;

  /** 我的空间内容 */
  private TeamPublishRowDomain mySpaceRow;

  /** 我的常用内容 */
  private List<NavDataItemDomain> myCommonUseFileList;
}
