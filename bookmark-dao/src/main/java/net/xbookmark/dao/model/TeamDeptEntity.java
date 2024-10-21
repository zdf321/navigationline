package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 团队部门
 *
 * @author zhangdingfei
 * @date 2023/7/17 22:03
 */
@Data
@TableName("tb_team_dept")
public class TeamDeptEntity extends BaseEntity {

  /** 主键 */
  @TableId private String id;

  /** 团队id */
  private String teamId;

  /** 部门名称 */
  @TableField("`name`")
  private String name;

  /** 父级id，根节点用root表示 */
  private String pid;

  /** 启用禁用表示 0-启动 1-禁用 */
  @TableLogic private String delFlag;
}
