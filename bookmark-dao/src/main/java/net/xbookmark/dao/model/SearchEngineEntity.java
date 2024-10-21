package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/** 搜索引擎配置主表 @TableName tb_search_engine */
@TableName(value = "tb_search_engine")
@Data
public class SearchEngineEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /** 分类名 */
  private String categoryName;

  /** 是否默认 */
  private Integer isDefault;

  /** 排序 */
  private Integer sort;

  /** 启用禁用表示 0-启动 1-禁用 */
  @TableLogic private String delFlag;
}
