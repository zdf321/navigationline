package net.xbookmark.api.nav.web.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/** @Author: zigui.zdf @Date: 2022/3/22 22:59:24 @Description: */
@Data
@ApiModel
public class SmsCodeResponse {

  private String key;

  private long time;
}
