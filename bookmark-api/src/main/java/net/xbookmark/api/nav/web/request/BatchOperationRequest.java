package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/** @Author: zigui.zdf @Date: 2022/3/10 22:54:49 @Description: */
@Data
@ApiModel
public class BatchOperationRequest {

  @ApiModelProperty("操作类型，clone-复制 move-移动 toTrash-删除")
  @NotBlank(message = "操作类型不能为空")
  private String type;

  @ApiModelProperty("数据id")
  private List<String> ids;

  @ApiModelProperty("目录id，删除时不用传")
  private String folderId;
}
