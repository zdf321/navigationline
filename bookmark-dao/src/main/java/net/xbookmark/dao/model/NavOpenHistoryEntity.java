package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/** 打开的历史记录表 @TableName tb_nav_open_history */
@TableName(value = "tb_nav_open_history")
@Data
public class NavOpenHistoryEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /** 用户id */
  private String uid;

  /** 团队id */
  private String teamId;

  /** nav id */
  private String navId;
}
