package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/5 21:03
 */
@Data
@ApiModel
public class LoginRequest {

  @NotNull(message = "登录类型不能为空")
  @ApiModelProperty(value = "登录类型，0-账号密码登录 1-手机验证码登录")
  private Integer loginType;

  @NotBlank(message = "请输入账号")
  @ApiModelProperty(value = "手机or邮箱", required = true)
  private String account;

  @ApiModelProperty(value = "密码，loginType=0时必填")
  private String password;

  @ApiModelProperty(value = "验证码，loginType=1时必填")
  private String code;
}
