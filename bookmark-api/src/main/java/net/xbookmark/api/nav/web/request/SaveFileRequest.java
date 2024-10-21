package net.xbookmark.api.nav.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/22 23:02
 */
@Data
@ApiModel
public class SaveFileRequest {

  @ApiModelProperty(value = "id，编辑时传入,node id")
  private Long id;

  @NotNull(message = "请选择父目录")
  @ApiModelProperty(value = "父id")
  private Long pid;

  @NotBlank(message = "文件名称不能为空")
  @Length(max = 255, message = "文件名称不能超过255个字符")
  @ApiModelProperty(value = "文件名称")
  private String fname;

  @NotNull(message = "文件大小不能为空")
  @ApiModelProperty(value = "文件大小，单位：字节")
  private Long fsize;

  @NotBlank(message = "文件key不能为空")
  @ApiModelProperty(value = "文件key")
  private String fileKey;

  @Length(max = 128, message = "文件描述不能超过128个字符")
  @ApiModelProperty(value = "描述")
  private String desc;
}
