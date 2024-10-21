package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 我的收藏
 *
 * @author zhangdingfei
 * @date 2023/7/26 22:01
 */
@Data
@TableName("tb_nav_collect")
public class NavCollectEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /** 用户id */
  private String uid;

  /** 团队id */
  private String teamId;

  /** nav */
  private String navId;

  /** 排序 */
  private Integer sort;

  /** 启用禁用表示 0-启动 1-禁用 */
  @TableLogic private String delFlag;
}
