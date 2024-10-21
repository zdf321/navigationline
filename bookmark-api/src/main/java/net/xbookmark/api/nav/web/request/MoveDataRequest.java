package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/** @Author: zigui.zdf @Date: 2022/3/10 22:18:47 @Description: */
@Data
@ApiModel
public class MoveDataRequest {

  @ApiModelProperty("被移动的数据id")
  @NotNull(message = "数据id不能为空")
  private List<String> dataIds;

  @ApiModelProperty("目录id")
  @NotBlank(message = "目录id不能为空")
  private String folderId;

  @ApiModelProperty("空间id")
  private String spaceId;
}
