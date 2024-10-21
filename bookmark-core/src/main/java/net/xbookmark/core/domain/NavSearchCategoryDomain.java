package net.xbookmark.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/** @Author: zigui.zdf @Date: 2022/3/11 21:42:22 @Description: */
@Data
@ApiModel
public class NavSearchCategoryDomain {

  @ApiModelProperty("记录id")
  private String id;

  @ApiModelProperty("分类名")
  private String categoryName;

  @ApiModelProperty("是否默认")
  private boolean isDefault;

  @ApiModelProperty("子分类")
  private List<Child> childs;

  @Data
  public static class Child {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("是否本站搜索")
    private Integer isSelfWebsite;

    @ApiModelProperty("搜索地址，本站搜搜时该值为空")
    private String searchUrl;

    @ApiModelProperty("placeholder")
    private String placeholder;
  }
}
