package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/** 团队发布信息 @TableName tb_team_publish_info */
@TableName(value = "tb_team_publish_info")
@Data
public class TeamPublishInfoEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /** 团队id */
  private String teamId;

  /** 发布后的二级域名 */
  private String domain;

  /** 发布状态 0-未发布 1-已发布 */
  private Boolean published;

  /** 是否显示广告 0-不显示 1-显示 */
  private Boolean showAd;

  /** 是否校验权限 0-不校验 1-校验 */
  private Boolean checkAuth;

  /** 是否需要登录后才能访问 0-否 1-是 */
  private Boolean needLogin;
}
