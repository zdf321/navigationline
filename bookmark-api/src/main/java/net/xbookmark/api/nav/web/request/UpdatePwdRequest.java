package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/1 17:25
 */
@Data
@ApiModel
public class UpdatePwdRequest {

  @NotBlank(message = "旧密码不能为空")
  @ApiModelProperty(value = "旧密码", required = true)
  private String oldPwd;

  @NotBlank(message = "新密码不能为空")
  @Length(min = 6, max = 24, message = "密码长度至少6位，最多24位")
  @ApiModelProperty(value = "新密码", required = true)
  private String newPwd;
}
