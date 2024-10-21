package net.xbookmark.core.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/11/9 15:24
 */
@Data
public class WebsiteDetailDomain {

  private String id;

  private String pid;

  private String url;

  private String name;

  private String categoryName;

  private Integer hitsNum;

  private Integer collectNum;

  private Date createTime;

  private String desc;

  private String logo;

  private String bigDesc;

  private List<String> tags;

  private List<NavDataItemDomain> relationWebsites;
}
