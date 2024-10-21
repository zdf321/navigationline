package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/** 搜索引擎配置表 @TableName tb_search_engine_detail */
@TableName(value = "tb_search_engine_detail")
@Data
public class SearchEngineDetailEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /** 主表id */
  private String searchEngineId;

  /** 名称 */
  private String name;

  /** 是否是搜索本网站内容 */
  private Integer isSelfWebsite;

  /** 搜索引擎地址 */
  private String searchUrl;

  /** 输入提示 */
  private String placeholder;

  /** 排序 */
  private Integer sort;

  /** 启用禁用表示 0-启动 1-禁用 */
  @TableLogic private String delFlag;
}
