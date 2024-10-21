package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 团队空间
 *
 * @author zhangdingfei
 * @date 2023/7/17 22:27
 */
@Data
@TableName("tb_team_space")
public class TeamSpaceEntity extends BaseEntity {

  /** 主键 */
  @TableId private String id;

  /** 团队id */
  private String teamId;

  /** 用户id */
  private String uid;

  /** 空间名称 */
  @TableField("`name`")
  private String name;

  /** 空间描述 */
  @TableField("`desc`")
  private String desc;

  /** 空间logo */
  private String logoUrl;

  /** 空间最大文件数，-1或null时表示不限制 */
  private Integer maxFileCount;

  /** 空间类型，见TeamSpaceTypeEnum */
  private String spaceType;

  /** 是否开启水印 ，开启后，空间文档访问时会显示当前访客姓名和navigationline账号ID后6位，防止用户截屏或拍照泄密。 */
  private Boolean showWatermark;

  /** 启用禁用表示 0-启动 1-禁用 */
  @TableLogic private String delFlag;
}
