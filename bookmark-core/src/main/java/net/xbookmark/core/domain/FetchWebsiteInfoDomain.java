package net.xbookmark.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/28 20:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchWebsiteInfoDomain {

  private String title;

  private String description;

  private String keywords;

  private String logoUrl;
}
