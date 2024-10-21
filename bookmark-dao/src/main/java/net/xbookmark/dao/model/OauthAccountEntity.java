package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhangdingfei
 * @date 2023/6/19 16:15
 */
@Data
@TableName("tb_oauth_account")
public class OauthAccountEntity extends BaseEntity {
  /** 主键 */
  @TableId private String id;

  /**
   * 用户第三方系统的唯一id。在调用方集成该组件时，可以用uuid + source唯一确定一个用户
   *
   * @since 1.3.3
   */
  private String uuid;
  /** 用户名 */
  private String username;
  /** 用户昵称 */
  private String nickname;
  /** 用户头像 */
  private String avatar;
  /** 用户网址 */
  private String blog;
  /** 所在公司 */
  private String company;
  /** 位置 */
  private String location;
  /** 用户邮箱 */
  private String email;
  /** 用户备注（各平台中的用户个人介绍） */
  private String remark;
  /** 性别 */
  private String gender;
  /** 用户来源 */
  private String source;
  /** 第三方平台返回的原始用户信息 */
  private String rawUserInfo;
}
