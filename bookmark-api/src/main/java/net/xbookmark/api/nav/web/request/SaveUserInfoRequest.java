package net.xbookmark.api.nav.web.request;

import lombok.Data;

/**
 * @author zhangdingfei
 * @date 2023/12/4 20:37
 */
@Data
public class SaveUserInfoRequest {
  /** 昵称 */
  private String nickname;

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

  /** 头像 */
  private String avatar;
}
