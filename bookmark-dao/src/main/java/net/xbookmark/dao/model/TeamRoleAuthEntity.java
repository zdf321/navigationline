package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/** 团队角色权限配置 @TableName tb_team_role_auth */
@TableName(value = "tb_team_role_auth")
@Data
public class TeamRoleAuthEntity implements Serializable {
  /** 主键 */
  @TableId private String id;

  /** 团队角色key */
  private String teamRole;

  /** 权限点key */
  private String authKey;

  @TableField(exist = false)
  private static final long serialVersionUID = 1L;
}
