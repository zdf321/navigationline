package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class HitRequest {

  @ApiModelProperty(value = "当前选中的id")
  @NotNull(message = "id必填")
  private String id;
}
