//package net.xbookmark.api.service.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import net.xbookmark.api.nav.web.request.CollectRequest;
//import net.xbookmark.api.nav.web.response.NavCategoryResponse;
//import net.xbookmark.api.nav.web.response.NavResponse;
//import net.xbookmark.api.nav.web.response.NavSearchResponse;
//import net.xbookmark.api.service.ToolService;
//import net.xbookmark.common.util.ConvertUtil;
//import net.xbookmark.config.NavProperties;
//import net.xbookmark.core.OSSService;
//import net.xbookmark.core.SystemToolNavService;
//import net.xbookmark.dao.mapper.SystemToolNavMapper;
//import net.xbookmark.dao.model.SystemToolNavEntity;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///** @Author: zigui.zdf @Date: 2022/3/14 23:15:58 @Description: */
//@Service
//@Slf4j
//public class ToolServiceImpl implements ToolService {
//
//  @Autowired private SystemToolNavMapper systemToolNavMapper;
//
//  @Autowired private NavProperties navProperties;
//
//  @Autowired private OSSService ossService;
//
//  @Autowired private SystemToolNavService systemToolNavService;
//
//  private static final Integer pageSize = 30;
//
//  @Override
//  public List<NavCategoryResponse> loadCategory() {
//    final Criteria criteria = Criteria.where("pid").is("root");
//    Query query = new Query(criteria);
//    final Sort sort = Sort.by(Sort.Direction.ASC, "sort");
//    final List<SystemToolNavEntity> multi = systemToolNavMapper.findMulti(query, sort);
//
//    List<NavCategoryResponse> responses = new ArrayList<>();
//
//    for (SystemToolNavEntity entity : multi) {
//      final NavCategoryResponse navCategoryResponse =
//          ConvertUtil.copyProperties(entity, NavCategoryResponse.class);
//
//      responses.add(navCategoryResponse);
//    }
//
//    return responses;
//  }
//
//  @Override
//  public List<NavResponse> loadNav(String pid, Integer pageNum) {
//    final Criteria criteria = Criteria.where("pid").is(pid);
//    Query query = new Query(criteria);
//
//    query.skip((pageNum - 1) * pageSize).limit(pageSize);
//
//    final Sort sort = Sort.by(Sort.Direction.ASC, "sort");
//
//    final List<SystemToolNavEntity> multi = systemToolNavMapper.findMulti(query, sort);
//
//    List<NavResponse> responses = convertToResponse(multi);
//
//    return responses;
//  }
//
//  private List<NavResponse> convertToResponse(List<SystemToolNavEntity> multi) {
//    List<NavResponse> responses = new ArrayList<>();
//
//    for (SystemToolNavEntity entity : multi) {
//      final NavResponse navResponse = ConvertUtil.copyProperties(entity, NavResponse.class);
//
//      navResponse.setTags(entity.getTags());
//
//      final String publicFullUrl =
//          ossService.getPublicFullUrl(
//              navProperties.getQiniu().getBucketPublicDomain(), entity.getLogoKey());
//      NavResponse.WebsiteInfo websiteInfo = new NavResponse.WebsiteInfo();
//      websiteInfo.setLogo(publicFullUrl);
//      websiteInfo.setUrl(entity.getUrl());
//      navResponse.setWebsiteInfo(websiteInfo);
//
//      responses.add(navResponse);
//    }
//    return responses;
//  }
//
//  @Override
//  public List<NavSearchResponse> searchNav(String search, Integer pageNum) {
//    Query query = new Query();
//
//    final Criteria criteria = new Criteria();
//
//    final Criteria dataTypeCriteria = Criteria.where("pid").ne("root");
//    criteria.andOperator(dataTypeCriteria);
//
//    query.skip((pageNum - 1) * pageSize).limit(pageSize);
//
//    if (StringUtils.isNotBlank(search)) {
//      final Criteria nameCriteria = Criteria.where("name").regex("^.*" + search + ".*$");
//      final Criteria descCriteria = Criteria.where("desc").regex("^.*" + search + ".*$");
//      criteria.orOperator(nameCriteria, descCriteria);
//    }
//
//    query.addCriteria(criteria);
//
//    final Sort sort = Sort.by(Sort.Direction.ASC, "sort");
//
//    final List<SystemToolNavEntity> multi = systemToolNavMapper.findMulti(query, sort);
//
//    List<NavSearchResponse> responses = new ArrayList<>();
//
//    NavSearchResponse response = new NavSearchResponse();
//    response.setCategoryName("全部");
//    response.setChilds(convertToResponse(multi));
//    responses.add(response);
//
//    final Map<String, List<SystemToolNavEntity>> listMap =
//        multi.stream().collect(Collectors.groupingBy(SystemToolNavEntity::getPid));
//
//    Query query1 = new Query(Criteria.where("_id").in(listMap.keySet()));
//    final List<SystemToolNavEntity> folders = systemToolNavMapper.findMulti(query1);
//
//    final Map<String, SystemToolNavEntity> navEntityMap =
//        folders.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
//
//    listMap.forEach(
//        (pid, entities) -> {
//          NavSearchResponse res = new NavSearchResponse();
//          res.setCategoryName(navEntityMap.get(pid).getName());
//          res.setChilds(convertToResponse(entities));
//          responses.add(res);
//        });
//
//    return responses;
//  }
//
//  @Override
//  public Boolean collect(CollectRequest request, String uid) {
//    systemToolNavService.clone(request.getId(), request.getPid(), uid);
//    return true;
//  }
//}
