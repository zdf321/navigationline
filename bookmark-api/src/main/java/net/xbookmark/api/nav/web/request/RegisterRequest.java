package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/1 17:25
 */
@Data
@ApiModel
public class RegisterRequest {

  @NotBlank(message = "用户名不能为空")
  @ApiModelProperty(value = "用户名", required = true)
  private String account;

  @NotBlank(message = "密码不能为空")
  @Length(min = 6, max = 24, message = "密码长度至少6位，最多24位")
  @ApiModelProperty(value = "密码", required = true)
  private String password;
}
