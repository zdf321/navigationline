package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/5/15 14:06
 */
@Data
@ApiModel
public class OSSTokenResponse {

  @ApiModelProperty(value = "token值")
  private String token;

  @ApiModelProperty(value = "key值")
  private String key;
}
