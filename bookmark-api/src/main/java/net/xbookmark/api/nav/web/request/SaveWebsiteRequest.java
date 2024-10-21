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
public class SaveWebsiteRequest {

  @ApiModelProperty(value = "id，编辑时传入,node id")
  private String id;

  @NotNull(message = "请选择父目录")
  @ApiModelProperty(value = "父id")
  private String pid;

  @NotBlank(message = "网站名称不能为空")
  @Length(max = 255, message = "网站名称不能超过255个字符")
  @ApiModelProperty(value = "网站名称")
  private String name;

  @Length(max = 255, message = "网站描述不能超过255个字符")
  @ApiModelProperty(value = "描述")
  private String desc;

  @NotNull(message = "网站地址不能为空")
  @Length(max = 2048, message = "网站地址不能超过2048个字符")
  @ApiModelProperty(value = "网站地址")
  private String url;

  @ApiModelProperty(value = "网站logoUrl")
  private String logoUrl;

  @ApiModelProperty(value = "关键词")
  private String keywords;

  @ApiModelProperty(value = "空间id")
  private String spaceId;
}
