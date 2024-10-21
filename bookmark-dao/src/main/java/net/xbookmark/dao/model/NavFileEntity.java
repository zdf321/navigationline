package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/** 文件信息表 @TableName tb_nav_file */
@TableName(value = "tb_nav_file")
@Data
public class NavFileEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /** nav id */
  private String navId;

  /** 启用禁用表示 0-启动 1-禁用 */
  private String delFlag;
}
