//package net.xbookmark.api.service.impl;
//
//import com.google.common.collect.Lists;
//import net.xbookmark.api.nav.web.response.HotWebsiteResponse;
//import net.xbookmark.api.service.HotService;
//import net.xbookmark.common.util.Constants;
//import net.xbookmark.service.NavHotKeywordService;
//import net.xbookmark.service.NavNodeService;
//import net.xbookmark.service.NavWebsiteService;
//import net.xbookmark.service.OSSService;
//import net.xbookmark.service.domain.NavNodeDomain;
//import net.xbookmark.service.domain.NavWebsiteDomain;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * @author: zigui.zdf
// * @description:
// * @date: 2020/12/13 21:46
// */
//@Service
//public class HotServiceImpl implements HotService {
//
//  @Autowired private NavHotKeywordService navHotKeywordService;
//
//  @Autowired private NavNodeService navNodeService;
//
//  @Autowired private NavWebsiteService navWebsiteService;
//
//  @Autowired private OSSService ossService;
//
//  @Autowired private RedisTemplate redisTemplate;
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
//  public List<String> keywords() {
//    final List<String> keywords = navHotKeywordService.getKeywords();
//    return keywords;
//  }
//
//  @Override
//  public List<HotWebsiteResponse> getHotWebsites() {
//    List<Long> ids = redisTemplate.opsForList().range(Constants.RedisKey.HOT_WEBSITE_KEY, 0, -1);
//    if (CollectionUtils.isEmpty(ids)) {
//      ids = navNodeService.getHotWebsiteIds();
//    }
//
//    final List<NavNodeDomain> navNodeDomains = navNodeService.getByIds(ids);
//
//    final List<Long> websiteIds =
//        navNodeDomains.stream()
//            .map(n -> n.getResourceId())
//            .filter(Objects::nonNull)
//            .collect(Collectors.toList());
//
//    final List<NavWebsiteDomain> websiteDomains = navWebsiteService.getWebsitesByIds(websiteIds);
//
//    final Map<Long, NavWebsiteDomain> websiteMap =
//        websiteDomains.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
//
//    List<HotWebsiteResponse> responses = Lists.newLinkedList();
//
//    for (NavNodeDomain navNodeDomain : navNodeDomains) {
//      HotWebsiteResponse response = new HotWebsiteResponse();
//      response.setId(navNodeDomain.getId());
//      response.setName(navNodeDomain.getName());
//
//      final NavWebsiteDomain navWebsiteDomain = websiteMap.get(navNodeDomain.getResourceId());
//      if (null != navWebsiteDomain) {
//        response.setLogo(
//            ossService.getPublicFullUrl(publicBucketDomain, navWebsiteDomain.getLogoKey()));
//        response.setUrl(navWebsiteDomain.getUrl());
//      }
//      responses.add(response);
//    }
//
//    return responses;
//  }
//}
