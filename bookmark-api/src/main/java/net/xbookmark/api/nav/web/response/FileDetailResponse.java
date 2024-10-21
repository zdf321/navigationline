package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/5/16 17:00
 */
@Data
@ApiModel
public class FileDetailResponse {

  @ApiModelProperty(value = "节点id")
  private Long id;

  @ApiModelProperty(value = "下载地址")
  private String downloadUrl;

  @ApiModelProperty(value = "文件名")
  private String name;

  @ApiModelProperty(value = "分类名（所属目录名）")
  private String typeName;

  @ApiModelProperty(value = "点击量")
  private Integer hitsNum;

  @ApiModelProperty(value = "收藏量")
  private Integer collectNum;

  @ApiModelProperty(value = "收录时间（创建时间）")
  private Date createTime;

  @ApiModelProperty(value = "描述")
  private String desc;

  @ApiModelProperty(value = "文件大小")
  private String fileSize;
}
