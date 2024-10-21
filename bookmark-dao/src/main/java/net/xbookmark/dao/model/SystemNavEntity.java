package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author: zigui.zdf @Date: 2022/3/14 23:17:16 @Description:
 */
@Data
@TableName("tb_system_nav")
public class SystemNavEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  private String pid;

  private String name;

  private String key;

  private String desc;

  private String icon;

  private Integer dataType;

  private double sort;

  private Integer rowState;

  private Integer hitsNum;

  private Integer collectNum;

  private List<String> tags;

  private Website website;

  private File file;

  @Data
  public static class Website {
    private String url;

    private String logoKey;

    private String keywords;

    private String screenshotKeys;
  }

  public static class File {}
}
