package net.xbookmark.api.nav.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangdingfei
 * @date 2023/7/17 21:30
 */
@Data
public class AddTeamRequest {

  @NotBlank(message = "企业或组织名不能为空")
  @Length(max = 20, message = "企业或组织名长度不能超过20个字符")
  private String orgName;

  @NotBlank(message = "您的真实姓名不能为空")
  @Length(max = 20, message = "您的真实姓名长度不能超过20个字符")
  private String userName;

  private String scale;

  private String businessType;
}
