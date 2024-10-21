package net.xbookmark.core.domain;

import lombok.Data;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/10/11 21:10
 */
@Data
public class TeamPublishRowDomain {

  /** 空间id */
  private String spaceId;

  /** 空间名 */
  private String spaceName;

  /** 空间类型 */
  private String spaceType;

  /** 空间logo */
  private String spaceLogoUrl;

  /** 空间下的第一层文件夹 */
  private List<NavDataItemDomain> folders;

  /** 第1个tab下的数据 */
  private List<NavDataItemDomain> files;
}
