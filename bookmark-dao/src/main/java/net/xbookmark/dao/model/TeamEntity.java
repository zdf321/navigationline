package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangdingfei
 * @date 2023/7/16 21:55
 */
@Data
@TableName("tb_team")
public class TeamEntity extends BaseEntity {

  /** 主键 */
  @TableId private String id;

  /** 团队类型，见TeamTypeEnum */
  private String teamType;

  /** 企业或组织名称 */
  private String orgName;

  /** 创建者用户id */
  private String uid;

  /** 成员规模，见：TeamScaleEnum */
  private String scale;

  /** 业务类型 */
  private String businessType;

  /** VIP类型，见枚举：VIPEnum */
  private String vipType;

  /** 到期时间 */
  private Date expireTime;

  /** 团队最大成员数 */
  private Integer maxPeople;

  /** 启用禁用表示 0-启动 1-禁用 */
  @TableLogic private String delFlag;
}
