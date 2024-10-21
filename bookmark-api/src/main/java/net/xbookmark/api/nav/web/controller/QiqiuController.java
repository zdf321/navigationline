package net.xbookmark.api.nav.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.xbookmark.api.nav.web.response.OSSTokenResponse;
import net.xbookmark.api.service.QiniuService;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.R;
import net.xbookmark.common.response.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/5/15 13:44
 */
//@RestController
//@RequestMapping("/qiniu")
//@Api(tags = "七牛云相关API")
public class QiqiuController {

  @Autowired private QiniuService qiniuService;

  @PostMapping("/getToken")
  @ApiOperation(value = "获取token，60秒有效期")
  public R<OSSTokenResponse> getToken(
      @ApiParam(value = "文件名", required = true) @RequestParam String fileName) {
    if (StringUtils.isBlank(fileName)) {
      throw new BusinessException(StatusCode.FAILED, "请传入文件名");
    }
    return R.success(qiniuService.getUploadToken(fileName));
  }
}
