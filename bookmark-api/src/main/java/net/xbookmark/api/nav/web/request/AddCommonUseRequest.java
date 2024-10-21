package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangdingfei
 * @date 2023/7/26 23:06
 */
@Data
@ApiModel
public class AddCommonUseRequest {

  /** 目标nav */
  @ApiModelProperty(value = "目标nav")
  @NotBlank(message = "navId不能为空")
  private String navId;

  /** 添加到那个team */
  @ApiModelProperty(value = "添加到哪个team")
  @NotBlank(message = "teamId不能为空")
  private String teamId;
}
