package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @Author: zigui.zdf @Date: 2022/4/3 12:49:51 @Description: */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class MyDataCountResponse {

  @ApiModelProperty("文件数")
  private long fileCount;

  @ApiModelProperty("总数")
  private long totalCount;
}
