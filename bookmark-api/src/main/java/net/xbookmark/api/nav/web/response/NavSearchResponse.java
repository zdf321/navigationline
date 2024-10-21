package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/** @Author: zigui.zdf @Date: 2022/3/11 22:20:48 @Description: */
@Data
@ApiModel
public class NavSearchResponse {

  @ApiModelProperty("分类名")
  private String categoryName;

  @ApiModelProperty("分类下面的内容")
  private List<NavResponse> childs;
}
