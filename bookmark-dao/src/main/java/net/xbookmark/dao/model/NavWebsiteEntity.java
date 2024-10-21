package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/** 网站信息表 @TableName tb_nav_website */
@TableName(value = "tb_nav_website")
@Data
public class NavWebsiteEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /** nav id */
  private String navId;

  /** 网站地址 */
  private String url;

  /** logo地址 */
  private String logoKey;

  /** 网站长描述 */
  @TableField("`desc`")
  private String desc;

  /** 启用禁用表示 0-启动 1-禁用 */
  private String delFlag;
}
