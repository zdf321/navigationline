package net.xbookmark.api.service;

import net.xbookmark.api.nav.web.response.OSSTokenResponse;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/5/15 13:47
 */
public interface QiniuService {

  OSSTokenResponse getUploadToken(String fileName);
}
