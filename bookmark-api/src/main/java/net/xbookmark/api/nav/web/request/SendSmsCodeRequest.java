package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/** @Author: zigui.zdf @Date: 2022/3/22 23:03:14 @Description: */
@Data
@ApiModel
public class SendSmsCodeRequest {

  @NotBlank(message = "手机号不能为空")
  @Pattern(regexp = "^[1][3,4,5,7,8,9][0-9]{9}")
  @ApiModelProperty(value = "手机号码", required = true)
  private String phone;
}
