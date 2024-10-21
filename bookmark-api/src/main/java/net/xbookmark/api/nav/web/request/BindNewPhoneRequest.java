package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/1 17:25
 */
@Data
@ApiModel
public class BindNewPhoneRequest {

  @NotBlank(message = "手机号不能为空")
  @Pattern(regexp = "^[1][3,4,5,7,8,9][0-9]{9}")
  @ApiModelProperty(value = "手机号码", required = true)
  private String phone;

  @NotBlank(message = "验证码不能为空")
  @ApiModelProperty(value = "验证码", required = true)
  private String code;
}
