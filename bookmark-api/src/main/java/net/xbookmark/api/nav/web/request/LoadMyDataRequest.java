package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/** @Author: zigui.zdf @Date: 2022/3/10 21:52:04 @Description: */
@Data
@ApiModel
public class LoadMyDataRequest {

  @ApiModelProperty("搜索词")
  private String search;

  @ApiModelProperty("父级id,顶级值为root")
  @NotBlank(message = "父id必填")
  private String pid;

  @ApiModelProperty("排序类型，0-自定义排序 1-创建时间 2-最后修改时间 3-标题 4-类型")
  private Integer sortType = 0;

  @ApiModelProperty("页面")
  private Integer pageNum = 1;

  @ApiModelProperty("资源类型 data-我的文件,history-最近修改,trash-回收站")
  private String resource = "data";

  @ApiModelProperty("空间id")
  private String spaceId;
}
