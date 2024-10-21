package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/12/16 22:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class FirstLevelResponse {

  @ApiModelProperty(value = "id")
  private Long id;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "图标")
  private String icon;

  @ApiModelProperty(value = "路径")
  private String path;
}
