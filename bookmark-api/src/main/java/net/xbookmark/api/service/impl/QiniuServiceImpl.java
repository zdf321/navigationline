//package net.xbookmark.api.service.impl;
//
//import net.xbookmark.api.nav.web.response.OSSTokenResponse;
//import net.xbookmark.api.service.QiniuService;
//import net.xbookmark.common.util.ConvertUtil;
//import net.xbookmark.common.util.UserContext;
//import net.xbookmark.service.OSSService;
//import net.xbookmark.service.domain.OSSTokenDomain;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
///**
// * @author: zigui.zdf
// * @description:
// * @date: 2021/5/15 13:47
// */
//@Service
//public class QiniuServiceImpl implements QiniuService {
//
//  @Autowired private OSSService ossService;
//
//  @Value("${qiniu.bucket.public}")
//  private String publicBucket;
//
//  @Value("${qiniu.bucket.public.domain}")
//  private String publicBucketDomain;
//
//  @Value("${qiniu.bucket.private}")
//  private String privateBucket;
//
//  @Value("${qiniu.bucket.private.domain}")
//  private String privateBucketDomain;
//
//  @Override
//  public OSSTokenResponse getUploadToken(String fileName) {
//    String userUuid = UserContext.getUserUuid();
//    OSSTokenDomain ossTokenDomain =
//        ossService.getUploadFileToken(privateBucket, fileName, userUuid);
//    OSSTokenResponse ossTokenResponse =
//        ConvertUtil.copyProperties(ossTokenDomain, OSSTokenResponse.class);
//    return ossTokenResponse;
//  }
//}
