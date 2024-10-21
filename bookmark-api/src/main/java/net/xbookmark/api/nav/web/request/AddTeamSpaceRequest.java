package net.xbookmark.api.nav.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangdingfei
 * @date 2023/7/17 21:30
 */
@Data
public class AddTeamSpaceRequest {

  @NotBlank(message = "团队id不能为空")
  private String teamId;

  @NotBlank(message = "空间名称不能为空")
  @Length(max = 20, message = "空间名称长度不能超过20个字符")
  private String name;

  @Length(max = 128, message = "空间描述不能超过128个字符")
  private String desc;
}
