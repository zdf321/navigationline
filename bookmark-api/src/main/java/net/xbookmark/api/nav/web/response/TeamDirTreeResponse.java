package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/8/20 15:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class TeamDirTreeResponse {

  @ApiModelProperty(value = "目录id")
  private String dirId;

  @ApiModelProperty(value = "目录名")
  private String label;

  @ApiModelProperty(value = "前端组件value值")
  private String value;

  @ApiModelProperty(value = "是否禁止选中")
  private Boolean disable;

  @ApiModelProperty(value = "所属空间id")
  private String spaceId;

  @ApiModelProperty(value = "空间类型")
  private String spaceType;

  @ApiModelProperty(value = "子节点")
  private List<TeamDirTreeResponse> children;
}
