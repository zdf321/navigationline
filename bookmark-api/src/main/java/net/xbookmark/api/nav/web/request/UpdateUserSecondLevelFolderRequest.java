package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdateUserSecondLevelFolderRequest {

  @ApiModelProperty(value = "id")
  @NotNull(message = "id不能为空")
  private Long id;

  @ApiModelProperty(value = "名称")
  @NotBlank(message = "名称不能为空")
  @Length(max = 10, message = "名称不能超过10个字符")
  private String name;
}
