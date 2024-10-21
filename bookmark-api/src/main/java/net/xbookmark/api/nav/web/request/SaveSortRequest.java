package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** @Author: zigui.zdf @Date: 2022/3/10 22:05:13 @Description: */
@Data
@ApiModel
public class SaveSortRequest {

  @NotBlank(message = "数据id不能为空")
  @ApiModelProperty("数据id")
  private String id;

  @ApiModelProperty("排序值")
  @NotNull(message = "排序值不能为空")
  private Integer sortNum;
}
