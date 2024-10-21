package net.xbookmark.core.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/10/11 22:05
 */
@Data
public class NavDatasDomain {

  private List<NavDataItemDomain> folders = new ArrayList<>();

  private List<NavDataItemDomain> files = new ArrayList<>();

  private Integer sortType;
}
