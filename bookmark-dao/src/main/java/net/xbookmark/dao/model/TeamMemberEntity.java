package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 团队成员
 *
 * @author zhangdingfei
 * @date 2023/7/17 21:52
 */
@Data
@TableName("tb_team_member")
public class TeamMemberEntity extends BaseEntity {

  /** 主键 */
  @TableId private String id;

  /** 团队id */
  private String teamId;

  /** 用户id */
  private String uid;

  /** 成员名称 */
  private String nickname;

  /** 加入时间 */
  private Date joinTime;

  /** 加入方式，见JoinWayEnum */
  private Integer joinWay;

  /** 邀请者 */
  private String inviteUid;

  /** 所属角色 */
  private String teamRole;

  /** 所属部门id */
  private String deptId;

  /** 删除标识 0-未删除 1-删除 */
  private String delFlag;
}
