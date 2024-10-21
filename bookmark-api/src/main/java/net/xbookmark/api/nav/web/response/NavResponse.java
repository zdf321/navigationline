package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/8 21:29
 */
@Data
@ApiModel
public class NavResponse {

  @ApiModelProperty(value = "id")
  private String id;

  @ApiModelProperty(value = "父id")
  private String pid;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "描述")
  private String desc;

  @ApiModelProperty(value = "类型，1-目录 2-网站 3-文件")
  private Integer dataType;

  @ApiModelProperty(value = "点击量")
  private Integer hitsNum;

  @ApiModelProperty(value = "收藏量")
  private Integer collectNum;

  @ApiModelProperty(value = "标签")
  private List<String> tags;

  @ApiModelProperty(value = "网站信息")
  private WebsiteInfo websiteInfo;

  @ApiModelProperty(value = "文件信息")
  private FileInfo fileInfo;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @Data
  public static class WebsiteInfo {

    @ApiModelProperty(value = "网站地址")
    private String url;

    @ApiModelProperty(value = "网站logo")
    private String logo;
  }

  @Data
  public static class FileInfo {}
}
