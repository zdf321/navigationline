package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class WebsiteInfoResponse {

  @ApiModelProperty(value = "标题")
  private String title;

  @ApiModelProperty(value = "描述")
  private String description;

  private String keywords;

  private String logoUrl;
}
