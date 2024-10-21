package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 团队空间成员
 *
 * @author zhangdingfei
 * @date 2023/7/17 22:47
 */
@Data
@TableName("tb_team_space_member")
public class TeamSpaceMemberEntity extends BaseEntity {

  /** 主键 */
  @TableId private String id;

  /** 空间id */
  private String spaceId;

  /** 用户id */
  private String uid;

  /** 空间角色 */
  private String spaceRole;

  /** 加入时间 */
  private Date joinTime;
}
