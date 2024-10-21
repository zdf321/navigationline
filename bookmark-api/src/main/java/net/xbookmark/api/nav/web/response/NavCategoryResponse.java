package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/** @Author: zigui.zdf @Date: 2022/3/11 21:56:26 @Description: */
@Data
@ApiModel
public class NavCategoryResponse {

  @ApiModelProperty("记录id")
  private String id;

  @ApiModelProperty("名称")
  private String name;

  @ApiModelProperty("icon图标")
  private String icon;

  @ApiModelProperty("key值，可能为空")
  private String key;

  @ApiModelProperty("子分类")
  private List<Child> childs;

  @Data
  public static class Child {

    @ApiModelProperty("记录id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("key值，可能为空")
    private String key;
  }
}
