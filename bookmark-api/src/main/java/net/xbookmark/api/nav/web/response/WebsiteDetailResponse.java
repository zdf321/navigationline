package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/5/16 16:21
 */
@Data
@ApiModel
public class WebsiteDetailResponse {
  @ApiModelProperty(value = "节点id")
  private String id;

  @ApiModelProperty(value = "节点父id")
  private String pid;

  @ApiModelProperty(value = "网页地址")
  private String url;

  @ApiModelProperty(value = "网站名")
  private String name;

  @ApiModelProperty(value = "分类名（所属目录名）")
  private String categoryName;

  @ApiModelProperty(value = "点击量")
  private Integer hitsNum;

  @ApiModelProperty(value = "收藏量")
  private Integer collectNum;

  @ApiModelProperty(value = "收录时间（创建时间）")
  private Date createTime;

  @ApiModelProperty(value = "关键词")
  private String keywords;

  @ApiModelProperty(value = "描述")
  private String desc;

  @ApiModelProperty(value = "网站logo")
  private String logo;

  @ApiModelProperty(value = "标签")
  private List<String> tags;

  @ApiModelProperty(value = "网页截图")
  private List<String> screenshots;

  @ApiModelProperty(value = "相关网站")
  private List<NavResponse> relationWebsites;
}
