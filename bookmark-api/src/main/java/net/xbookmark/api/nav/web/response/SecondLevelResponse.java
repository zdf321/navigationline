package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/1 09:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SecondLevelResponse {

  @ApiModelProperty(value = "id")
  private Long id;

  @ApiModelProperty(value = "名称")
  private String name;
}
