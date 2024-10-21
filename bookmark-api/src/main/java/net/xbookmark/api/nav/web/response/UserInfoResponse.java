package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/5 21:06
 */
@Data
@ApiModel
public class UserInfoResponse {

  @ApiModelProperty(value = "用户id")
  private String uid;

  @ApiModelProperty(value = "头像")
  private String avatar;

  /** 手机 */
  private String username;

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

  @ApiModelProperty(value = "个人空间团队id")
  private String myTeamId;

  @ApiModelProperty(value = "个人空间团队空间id")
  private String myTeamSpaceId;
}
