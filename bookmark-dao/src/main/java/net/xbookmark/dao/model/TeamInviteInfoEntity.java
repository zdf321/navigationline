package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @TableName tb_team_invite_info
 */
@TableName(value = "tb_team_invite_info")
@Data
public class TeamInviteInfoEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /** 邀请id，uuid */
  private String inviteId;

  /** 团队id */
  private String teamId;

  /** 是否开启团队邀请链接 0-关闭 1-开启 */
  private Integer inviteLinkOpen;

  /** 团队邀请链接有效期（天）-1表示永久有效 */
  private Integer inviteLinkDays;

  /** 团队邀请链接开启时间 */
  private Date inviteLinkStartTime;

  /** 邀请者id */
  private String uid;
}
