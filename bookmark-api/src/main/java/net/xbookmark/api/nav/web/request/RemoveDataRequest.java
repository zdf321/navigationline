package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/** @Author: zigui.zdf @Date: 2022/3/10 22:26:49 @Description: */
@Data
@ApiModel
public class RemoveDataRequest {

  @ApiModelProperty("数据id")
  @NotNull(message = "数据id不能为空")
  private List<String> ids;
}
