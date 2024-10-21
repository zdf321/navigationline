package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/22 23:02
 */
@Data
@ApiModel
public class SaveFolderRequest {

  @ApiModelProperty(value = "id，编辑时传入")
  private String id;

  @NotBlank(message = "请传入父目录id")
  @ApiModelProperty(value = "父目录id")
  private String pid;

  @NotBlank(message = "目录名称不能为空")
  @Length(max = 255, message = "目录名称不能超过255个字符")
  @ApiModelProperty(value = "名称")
  private String name;

  @Length(max = 128, message = "文件夹描述不能超过128个字符")
  @ApiModelProperty(value = "描述")
  private String desc;

  @ApiModelProperty(value = "空间id")
  private String spaceId;
}
