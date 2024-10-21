package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author: zigui.zdf @Date: 2022/3/14 23:17:16 @Description:
 */
@Data
@TableName("tb_system_tool_nav")
public class SystemToolNavEntity extends BaseEntity {

  /** 主键 */
  @TableId private String id;

  private String pid;

  private String name;

  private String desc;

  private String icon;

  private double sort;

  private Integer rowState;

  private Integer hitsNum;

  private Integer collectNum;

  private List<String> tags;

  private String url;

  private String logoKey;
}
