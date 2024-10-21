package net.xbookmark.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_account")
public class AccountEntity extends BaseEntity {

  /** 主键 */
  @TableId private String id;

  /** 昵称 */
  private String nickname;

  /** 邮箱 */
  private String email;

  /** 手机 */
  private String username;

  /** 密码 */
  private String password;

  /** 盐值 */
  private String salt;

  /** 头像 */
  private String avatar;

  /** 性别 */
  private String gender;

  /** 行业 */
  private String industry;

  /** 公司 */
  private String company;

  /** 职业 */
  private String profession;

  /** 学历 */
  private String education;

  /** 个人简介 */
  private String remark;

  /** 启用禁用表示 0-启动 1-禁用 */
  @TableLogic private String delFlag;
}
