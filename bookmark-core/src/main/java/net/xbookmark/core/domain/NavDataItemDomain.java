package net.xbookmark.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangdingfei
 * @date 2023/7/26 22:13
 */
@Data
public class NavDataItemDomain {

  @ApiModelProperty("数据id")
  private String id;

  @ApiModelProperty("父级id")
  private String pid;

  @ApiModelProperty("数据类型，1-目录，2-网站，参见：DataTypeEnum")
  private Integer dataType;

  @ApiModelProperty("名称")
  private String name;

  @ApiModelProperty("描述")
  private String desc;

  @ApiModelProperty("logo地址")
  private String logo;

  @ApiModelProperty("最后修改时间")
  private Date updateTime;

  @ApiModelProperty("拥有者")
  private String owner;

  @ApiModelProperty("地址")
  private String url;

  @ApiModelProperty("排序值")
  private double sortNum;

  @ApiModelProperty("是否在我的常用列表中")
  private Boolean inCommonUse;
}
