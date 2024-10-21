package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/5/30 17:14
 */
@Data
@ApiModel
public class HotWebsiteResponse {

  @ApiModelProperty(value = "节点id")
  private Long id;

  @ApiModelProperty(value = "网站名")
  private String name;

  @ApiModelProperty(value = "logo地址")
  private String logo;

  @ApiModelProperty(value = "链接地址")
  private String url;
}
