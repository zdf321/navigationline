package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/23 21:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class MyDirTreeResponse {

  @ApiModelProperty(value = "id")
  private String id;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "子节点")
  private List<MyDirTreeResponse> children;
}
