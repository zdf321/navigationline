package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/** 权限点表 @TableName tb_auth */
@TableName(value = "tb_auth")
@Data
public class AuthEntity implements Serializable {
  /** 主键 */
  @TableId private String id;

  /** 权限点key值 */
  private String authKey;

  /** 权限点名称 */
  private String authName;

  /** 权限点描述 */
  private String authDesc;

  @TableField(exist = false)
  private static final long serialVersionUID = 1L;
}
