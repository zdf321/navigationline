package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CollectRequest {

  @ApiModelProperty(value = "当前选中的id")
  @NotNull(message = "id必填")
  private String id;

  @ApiModelProperty(value = "父id")
  @NotNull(message = "父id必填")
  private String pid;
}
