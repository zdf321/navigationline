package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: zigui.zdf @Date: 2022/3/13 11:41:56 @Description:
 */
@Data
@TableName("tb_nav")
public class NavEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  private String teamId;

  private String spaceId;

  private String pid;

  @TableField("`name`")
  private String name;

  @TableField("`desc`")
  private String desc;

  private Integer dataType;

  private String uid;

  private Integer sort;

  /** 启用禁用表示 0-启动 1-禁用 */
  private String delFlag;

  /** 点击量 */
  private Integer hitsNum;

  /** 收藏量 */
  private Integer collectNum;

  /** 标签，多个用英文逗号隔开 */
  private String tags;
}
