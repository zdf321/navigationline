package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/** 我的常用表 @TableName tb_nav_common_use */
@TableName(value = "tb_nav_common_use")
@Data
public class NavCommonUseEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /** 用户id */
  private String uid;

  /** 团队id */
  private String teamId;

  /** nav id */
  private String navId;

  /** 排序 */
  private Integer sort;
}
