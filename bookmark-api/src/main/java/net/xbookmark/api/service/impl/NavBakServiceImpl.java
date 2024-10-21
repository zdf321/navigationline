//package net.xbookmark.api.service.impl;
//
//import com.google.common.collect.Lists;
//import com.qiniu.common.QiniuException;
//import lombok.extern.slf4j.Slf4j;
//import net.xbookmark.api.nav.web.request.CollectRequest;
//import net.xbookmark.api.nav.web.request.SaveFolderRequest;
//import net.xbookmark.api.nav.web.request.SaveFileRequest;
//import net.xbookmark.api.nav.web.request.SaveWebsiteRequest;
//import net.xbookmark.api.nav.web.response.*;
//import net.xbookmark.api.service.NavService;
//import net.xbookmark.common.enums.DataTypeEnum;
//import net.xbookmark.common.exception.BusinessException;
//import net.xbookmark.common.response.StatusCode;
//import net.xbookmark.common.util.ConvertUtil;
//import net.xbookmark.common.util.UserContext;
//import net.xbookmark.common.vo.UserInfo;
//import net.xbookmark.service.NavFileService;
//import net.xbookmark.service.NavNodeService;
//import net.xbookmark.service.NavWebsiteService;
//import net.xbookmark.service.OSSService;
//import net.xbookmark.service.domain.*;
//import net.xbookmark.service.param.SaveFileParam;
//import net.xbookmark.service.param.SaveNodeParam;
//import net.xbookmark.service.param.SaveWebsiteParam;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * @author: zigui.zdf
// * @description:
// * @date: 2020/11/1 10:00
// */
//@Service
//@Slf4j
//public class NavServiceImpl implements NavService {
//
//  @Autowired private NavNodeService navNodeService;
//
//  @Autowired private NavWebsiteService navWebsiteService;
//
//  @Autowired private NavFileService navFileService;
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
//  public List<FirstLevelResponse> getFirstLevelFolders() {
//
//    List<FirstLevelDomain> firstLevelFolders = navNodeService.getFirstLevelFolders();
//
//    List<FirstLevelResponse> responses = Lists.newArrayList();
//
//    for (FirstLevelDomain firstLevelFolder : firstLevelFolders) {
//      responses.add(ConvertUtil.copyProperties(firstLevelFolder, FirstLevelResponse.class));
//    }
//
//    return responses;
//  }
//
//  @Override
//  public List<SecondLevelResponse> getSecondLevelFolders(Long pid) {
//    List<SecondLevelResponse> responses = Lists.newArrayList();
//
//    UserInfo userInfo = UserContext.getUserInfo();
//    Long userId = userInfo != null ? userInfo.getUserId() : null;
//
//    List<SecondLevelDomain> domains =
//        navNodeService.getSecondLevelFoldersByFirstLevelFolderId(pid, userId);
//
//    for (SecondLevelDomain domain : domains) {
//      responses.add(ConvertUtil.copyProperties(domain, SecondLevelResponse.class));
//    }
//
//    return responses;
//  }
//
//  @Override
//  public List<SecondLevelResponse> getSecondLevelFoldersByPath(String path) {
//    List<SecondLevelResponse> responses = Lists.newArrayList();
//
//    UserInfo userInfo = UserContext.getUserInfo();
//    Long userId = userInfo != null ? userInfo.getUserId() : null;
//
//    List<SecondLevelDomain> domains =
//        navNodeService.getSecondLevelFoldersByFirstLevelPath(path, userId);
//
//    for (SecondLevelDomain domain : domains) {
//      responses.add(ConvertUtil.copyProperties(domain, SecondLevelResponse.class));
//    }
//
//    return responses;
//  }
//
//  @Override
//  public List<NavResponse> getOtherLevelItems(Long pid) {
//    List<NavResponse> otherLevelResponses = Lists.newArrayList();
//
//    List<NavNodeDomain> navNodeDomains = navNodeService.getNodesByPid(pid);
//
//    List<Long> websiteIds =
//        navNodeDomains.stream()
//            .filter(
//                domain -> Objects.equals(domain.getNodeType(), DataTypeEnum.WEBSITE.getDataType()))
//            .map(domain -> domain.getResourceId())
//            .collect(Collectors.toList());
//
//    List<Long> fileIds =
//        navNodeDomains.stream()
//            .filter(domain -> Objects.equals(domain.getNodeType(), DataTypeEnum.FILE.getDataType()))
//            .map(domain -> domain.getResourceId())
//            .collect(Collectors.toList());
//
//    List<NavWebsiteDomain> websiteDomains = navWebsiteService.getWebsitesByIds(websiteIds);
//    List<NavFileDomain> fileDomains = navFileService.getFilesByIds(fileIds);
//
//    Map<Long, NavWebsiteDomain> websiteMap =
//        websiteDomains.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
//    Map<Long, NavFileDomain> fileMap =
//        fileDomains.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
//
//    for (NavNodeDomain navNode : navNodeDomains) {
//      NavWebsiteDomain navWebsiteDomain = null;
//      NavFileDomain navFileDomain = null;
//
//      if (Objects.equals(navNode.getNodeType(), DataTypeEnum.WEBSITE.getDataType())) {
//        navWebsiteDomain = websiteMap.get(navNode.getResourceId());
//      }
//
//      if (Objects.equals(navNode.getNodeType(), DataTypeEnum.FILE.getDataType())) {
//        navFileDomain = fileMap.get(navNode.getResourceId());
//      }
//
//      NavResponse response =
//          this.getOtherLevelResponse(navNode, navWebsiteDomain, navFileDomain);
//
//      otherLevelResponses.add(response);
//    }
//
//    return otherLevelResponses;
//  }
//
//  private NavResponse getOtherLevelResponse(
//      NavNodeDomain navNode, NavWebsiteDomain navWebsiteDomain, NavFileDomain navFileDomain) {
//    NavResponse response = new NavResponse();
//    response.setId(navNode.getId());
//    response.setPid(navNode.getPid());
//    response.setIsSystem(navNode.getIsSystem());
//    response.setName(navNode.getName());
//    response.setDesc(navNode.getDesc());
//    response.setType(navNode.getNodeType());
//    response.setHitsNum(navNode.getHitsNum());
//    response.setCollectNum(navNode.getCollectNum());
//    response.setUpdateTime(navNode.getUpdateTime());
//
//    if (Objects.equals(navNode.getNodeType(), DataTypeEnum.WEBSITE.getDataType())
//        && null != navWebsiteDomain) {
//      NavResponse.WebsiteInfo websiteInfo =
//          NavResponse.WebsiteInfo.builder()
//              .url(navWebsiteDomain.getUrl())
//              .logo(ossService.getPublicFullUrl(publicBucketDomain, navWebsiteDomain.getLogoKey()))
//              .build();
//      response.setWebsiteInfo(websiteInfo);
//    }
//
//    if (Objects.equals(navNode.getNodeType(), DataTypeEnum.FILE.getDataType())
//        && null != navFileDomain) {
//      NavResponse.FileInfo fileInfo =
//          NavResponse.FileInfo.builder()
//              .url(ossService.getPrivateFullUrl(privateBucketDomain, navFileDomain.getFileKey()))
//              .fileKey(navFileDomain.getFileKey())
//              .build();
//      response.setFileInfo(fileInfo);
//    }
//
//    return response;
//  }
//
//  @Override
//  public SecondLevelResponse addUserSecondLevelFolder() {
//
//    Long userId = UserContext.getUserId();
//    Long pid = 1L;
//
//    String nodeName = navNodeService.getNextDefaultNodeNameByPid(pid, userId);
//
//    long sort = navNodeService.getNextSortByPid(pid, userId);
//
//    Long nodeId = navNodeService.addDirNode(nodeName, sort, pid, userId);
//
//    SecondLevelResponse response = new SecondLevelResponse(nodeId, nodeName);
//
//    return response;
//  }
//
//  @Override
//  @Transactional
//  public Boolean deleteUserNode(Long id, Boolean includeInnerNode) {
//    Long userId = UserContext.getUserId();
//    navNodeService.deleteNodeById(id, includeInnerNode, userId);
//    return true;
//  }
//
//  @Override
//  public String updateUserSecondLevelFolder(Long id, String name) {
//    Long userId = UserContext.getUserId();
//    navNodeService.updateNodeNameById(name, id, userId);
//    return name;
//  }
//
//  @Override
//  @Transactional
//  public NavResponse saveUserWebsite(SaveWebsiteRequest request) {
//
//    Long websiteId = null;
//    if (null != request.getId()) {
//      NavNodeDomain navNodeDomain = navNodeService.getById(request.getId());
//      if (null != navNodeDomain) {
//        websiteId = navNodeDomain.getResourceId();
//      }
//    }
//
//    Long userId = UserContext.getUserId();
//
//    String logoKey = null;
//    if (StringUtils.isNotBlank(request.getLogoUrl())) {
//      logoKey = request.getLogoUrl().substring(publicBucketDomain.length() + 1);
//      try {
//        logoKey = URLDecoder.decode(logoKey, "UTF-8");
//      } catch (UnsupportedEncodingException e) {
//
//      }
//    }
//
//    SaveWebsiteParam saveWebsiteParam = new SaveWebsiteParam();
//    saveWebsiteParam.setId(websiteId);
//    saveWebsiteParam.setUserId(userId);
//    saveWebsiteParam.setUrl(request.getUrl());
//    saveWebsiteParam.setLogo(logoKey);
//    saveWebsiteParam.setKeywords(request.getKeywords());
//
//    if (null != websiteId) {
//      NavWebsiteDomain navWebsiteDomain = navWebsiteService.getById(websiteId);
//      saveWebsiteParam.setKeywords(navWebsiteDomain.getKeywords());
//      saveWebsiteParam.setScreenshot(navWebsiteDomain.getScreenshotKeys());
//    }
//
//    websiteId = navWebsiteService.saveWebsite(saveWebsiteParam);
//
//    SaveNodeParam saveNodeParam = new SaveNodeParam();
//    saveNodeParam.setId(request.getId());
//    saveNodeParam.setPid(request.getPid());
//    saveNodeParam.setName(request.getName());
//    saveNodeParam.setUserId(userId);
//    saveNodeParam.setNodeType(DataTypeEnum.WEBSITE.getDataType());
//    saveNodeParam.setResourceId(websiteId);
//    saveNodeParam.setDesc(request.getDesc());
//
//    Long nodeId = navNodeService.saveUserNode(saveNodeParam);
//
//    NavNodeDomain navNodeDomain = navNodeService.getById(nodeId);
//    NavWebsiteDomain navWebsiteDomain = navWebsiteService.getById(websiteId);
//
//    NavResponse otherLevelResponse =
//        this.getOtherLevelResponse(navNodeDomain, navWebsiteDomain, null);
//
//    return otherLevelResponse;
//  }
//
//  @Override
//  public NavResponse saveUserDir(SaveFolderRequest request) {
//    Long userId = UserContext.getUserId();
//
//    SaveNodeParam saveNodeParam = new SaveNodeParam();
//    saveNodeParam.setId(request.getId());
//    saveNodeParam.setPid(request.getPid());
//    saveNodeParam.setName(request.getName());
//    saveNodeParam.setUserId(userId);
//    saveNodeParam.setNodeType(DataTypeEnum.DIR.getDataType());
//    saveNodeParam.setDesc(request.getDesc());
//
//    Long nodeId = navNodeService.saveUserNode(saveNodeParam);
//
//    NavNodeDomain navNodeDomain = navNodeService.getById(nodeId);
//
//    NavResponse otherLevelResponse = this.getOtherLevelResponse(navNodeDomain, null, null);
//    return otherLevelResponse;
//  }
//
//  @Override
//  public List<MyDirTreeResponse> getUserDirTree() {
//    List<MyDirTreeResponse> responses = Lists.newArrayList();
//
//    Long userId = UserContext.getUserId();
//
//    List<NavNodeDomain> domains =
//        navNodeService.getUserNodesByNodeType(DataTypeEnum.DIR.getDataType(), userId);
//
//    List<NavNodeDomain> pNodes =
//        domains.stream()
//            .filter(node -> Objects.equals(node.getPid(), 1L))
//            .collect(Collectors.toList());
//
//    for (NavNodeDomain pNode : pNodes) {
//      MyDirTreeResponse pTree =
//          new MyDirTreeResponse(pNode.getId(), pNode.getName(), Lists.newArrayList());
//      responses.add(pTree);
//      this.recursiveNode(pTree, domains);
//    }
//
//    return responses;
//  }
//
//  /**
//   * 递归节点
//   *
//   * @param pTree
//   * @param navNodes
//   */
//  private void recursiveNode(MyDirTreeResponse pTree, List<NavNodeDomain> navNodes) {
//    List<NavNodeDomain> childNodes =
//        navNodes.stream()
//            .filter(node -> Objects.equals(node.getPid(), pTree.getId()))
//            .collect(Collectors.toList());
//
//    if (CollectionUtils.isNotEmpty(childNodes)) {
//      for (NavNodeDomain childNode : childNodes) {
//        MyDirTreeResponse cTree =
//            new MyDirTreeResponse(childNode.getId(), childNode.getName(), Lists.newArrayList());
//        pTree.getChildren().add(cTree);
//
//        recursiveNode(cTree, navNodes);
//      }
//    }
//  }
//
//  @Override
//  public WebsiteInfoResponse getWebsiteInfo(String url) {
//    final FetchWebsiteInfoDomain fetchWebsiteInfoDomain = navWebsiteService.fetchWebsiteInfo(url);
//
//    return ConvertUtil.copyProperties(fetchWebsiteInfoDomain, WebsiteInfoResponse.class);
//  }
//
//  @Override
//  @Transactional
//  public Boolean collect(CollectRequest request) {
//    NavNodeDomain navNode = navNodeService.getById(request.getId());
//    if (null == navNode) {
//      throw new BusinessException(StatusCode.FAILED, "数据不存在");
//    }
//
//    Long userId = UserContext.getUserId();
//    String userUuid = UserContext.getUserUuid();
//
//    SaveNodeParam saveNodeParam = new SaveNodeParam();
//    saveNodeParam.setPid(request.getPid());
//    saveNodeParam.setName(navNode.getName());
//    saveNodeParam.setUserId(userId);
//    saveNodeParam.setNodeType(navNode.getNodeType());
//    saveNodeParam.setDesc(navNode.getDesc());
//
//    Long nodeId = navNodeService.saveUserNode(saveNodeParam);
//
//    Integer nodeType = navNode.getNodeType();
//
//    if (Objects.equals(DataTypeEnum.DIR.getDataType(), nodeType)) {
//      cloneDir(navNode.getId(), nodeId, userId, userUuid);
//    } else if (Objects.equals(DataTypeEnum.WEBSITE.getDataType(), nodeType)) {
//      Long websiteId = this.cloneWebsite(navNode.getResourceId(), userUuid);
//
//      navNodeService.updateResourceId(nodeId, websiteId);
//    } else if (Objects.equals(DataTypeEnum.FILE.getDataType(), nodeType)) {
//      Long fileId = this.cloneFile(navNode.getResourceId(), userUuid);
//      navNodeService.updateResourceId(nodeId, fileId);
//    }
//
//    return true;
//  }
//
//  @Override
//  public Boolean collectIndex(Long targetUserId, String targetUserUuid) {
//
//    Long indexMenuId = 1L;
//
//    final List<SecondLevelDomain> secondLevelFolders =
//        navNodeService.getSecondLevelFoldersByFirstLevelFolderId(indexMenuId, null);
//
//    for (SecondLevelDomain secondLevelFolder : secondLevelFolders) {
//      SaveNodeParam saveNodeParam = new SaveNodeParam();
//      saveNodeParam.setPid(indexMenuId);
//      saveNodeParam.setName(secondLevelFolder.getName());
//      saveNodeParam.setUserId(targetUserId);
//      saveNodeParam.setNodeType(DataTypeEnum.DIR.getDataType());
//
//      Long nodeId = navNodeService.saveUserNode(saveNodeParam);
//
//      cloneDir(secondLevelFolder.getId(), nodeId, targetUserId, targetUserUuid);
//    }
//
//    return true;
//  }
//
//  private Long cloneWebsite(Long websiteId, String userUuid) {
//    NavWebsiteDomain navWebsite = navWebsiteService.getById(websiteId);
//
//    String newLogo = null;
//    String newScreenshot = null;
//
//    Map<String, String> keyMap = new HashMap<>();
//
//    String logo = navWebsite.getLogoKey();
//    if (StringUtils.isNotBlank(logo)) {
//      newLogo = String.format("%s/%s", userUuid, logo);
//      keyMap.put(logo, newLogo);
//    }
//
//    String screenshot = navWebsite.getScreenshotKeys();
//    if (StringUtils.isNotBlank(screenshot)) {
//      String[] split = screenshot.split(",");
//      for (String s : split) {
//        String newShot = String.format("%s/%s", userUuid, s);
//        keyMap.put(s, newShot);
//        newScreenshot += newShot + ",";
//      }
//      if (newScreenshot.contains(",")) {
//        newScreenshot = newScreenshot.substring(0, newScreenshot.lastIndexOf(","));
//      }
//    }
//
//    try {
//      ossService.copyObject(keyMap, publicBucket);
//    } catch (QiniuException e) {
//      log.error(e.getMessage(), e);
//    }
//
//    SaveWebsiteParam saveWebsiteParam = new SaveWebsiteParam();
//    saveWebsiteParam.setUrl(navWebsite.getUrl());
//    saveWebsiteParam.setLogo(newLogo);
//    saveWebsiteParam.setKeywords(navWebsite.getKeywords());
//    saveWebsiteParam.setScreenshot(newScreenshot);
//
//    Long newWebsiteId = navWebsiteService.saveWebsite(saveWebsiteParam);
//
//    return newWebsiteId;
//  }
//
//  private Long cloneFile(Long fileId, String userUuid) {
//    NavFileDomain navFile = navFileService.getById(fileId);
//
//    String newUrl = null;
//
//    Map<String, String> keyMap = new HashMap<>();
//
//    String url = navFile.getFileKey();
//    if (StringUtils.isNotBlank(url)) {
//      newUrl = url.replaceFirst("\\w+/", userUuid + "/");
//      keyMap.put(url, newUrl);
//    }
//
//    try {
//      ossService.copyObject(keyMap, privateBucket);
//    } catch (QiniuException e) {
//      log.error(e.getMessage(), e);
//    }
//
//    SaveFileParam saveFileParam = new SaveFileParam();
//    saveFileParam.setFileKey(newUrl);
//    saveFileParam.setFileSize(navFile.getFileSize());
//    Long newFileId = navFileService.saveFile(saveFileParam);
//
//    return newFileId;
//  }
//
//  private void cloneDir(Long dirNodeId, Long newPid, Long userId, String userUuid) {
//    List<NavNodeDomain> navNodeDomains = navNodeService.getNodesByPid(dirNodeId);
//
//    for (NavNodeDomain navNode : navNodeDomains) {
//      SaveNodeParam saveNodeParam = new SaveNodeParam();
//      saveNodeParam.setPid(newPid);
//      saveNodeParam.setName(navNode.getName());
//      saveNodeParam.setUserId(userId);
//      saveNodeParam.setNodeType(navNode.getNodeType());
//      saveNodeParam.setDesc(navNode.getDesc());
//
//      Long nodeId = navNodeService.saveUserNode(saveNodeParam);
//
//      Integer nodeType = navNode.getNodeType();
//
//      if (Objects.equals(DataTypeEnum.DIR.getDataType(), nodeType)) {
//        cloneDir(navNode.getId(), nodeId, userId, userUuid);
//      } else if (Objects.equals(DataTypeEnum.WEBSITE.getDataType(), nodeType)) {
//        Long websiteId = this.cloneWebsite(navNode.getResourceId(), userUuid);
//
//        navNodeService.updateResourceId(nodeId, websiteId);
//      } else if (Objects.equals(DataTypeEnum.FILE.getDataType(), nodeType)) {
//        Long fileId = this.cloneFile(navNode.getResourceId(), userUuid);
//        navNodeService.updateResourceId(nodeId, fileId);
//      }
//    }
//  }
//
//  @Override
//  public WebsiteDetailResponse getWebsiteDetail(Long id) {
//    NavNodeDomain navNodeDomain = navNodeService.getById(id);
//
//    if (null == navNodeDomain || null == navNodeDomain.getResourceId()) {
//      throw new BusinessException(StatusCode.FAILED, "数据不存在");
//    }
//
//    NavWebsiteDomain navWebsiteDomain = navWebsiteService.getById(navNodeDomain.getResourceId());
//
//    if (null == navWebsiteDomain) {
//      throw new BusinessException(StatusCode.FAILED, "数据不存在");
//    }
//
//    NavNodeDomain pNodeDomain = navNodeService.getById(navNodeDomain.getPid());
//
//    WebsiteDetailResponse response = new WebsiteDetailResponse();
//    response.setId(id);
//    response.setPid(navNodeDomain.getPid());
//    response.setUrl(navWebsiteDomain.getUrl());
//    response.setName(navNodeDomain.getName());
//    response.setTypeName(pNodeDomain.getName());
//    response.setHitsNum(navNodeDomain.getHitsNum());
//    response.setCollectNum(navNodeDomain.getCollectNum());
//    response.setCreateTime(navNodeDomain.getCreateTime());
//    response.setKeywords(navWebsiteDomain.getKeywords());
//    response.setDesc(navNodeDomain.getDesc());
//    response.setLogo(
//        ossService.getPublicFullUrl(publicBucketDomain, navWebsiteDomain.getLogoKey()));
//
//    if (StringUtils.isNotBlank(navWebsiteDomain.getScreenshotKeys())) {
//      List<String> screenshots = Lists.newArrayList();
//      String[] split = navWebsiteDomain.getScreenshotKeys().split(",");
//      for (String s : split) {
//        screenshots.add(ossService.getPublicFullUrl(publicBucketDomain, s));
//      }
//      response.setScreenshots(screenshots);
//    }
//
//    return response;
//  }
//
//  @Override
//  public FileDetailResponse getFileDetail(Long id) {
//    NavNodeDomain navNodeDomain = navNodeService.getById(id);
//
//    if (null == navNodeDomain || null == navNodeDomain.getResourceId()) {
//      throw new BusinessException(StatusCode.FAILED, "数据不存在");
//    }
//
//    NavFileDomain navFileDomain = navFileService.getById(navNodeDomain.getResourceId());
//
//    if (null == navFileDomain) {
//      throw new BusinessException(StatusCode.FAILED, "数据不存在");
//    }
//
//    NavNodeDomain pNodeDomain = navNodeService.getById(navNodeDomain.getPid());
//
//    FileDetailResponse response = new FileDetailResponse();
//    response.setId(id);
//    response.setName(navNodeDomain.getName());
//    response.setTypeName(pNodeDomain.getName());
//    response.setHitsNum(navNodeDomain.getHitsNum());
//    response.setCollectNum(navNodeDomain.getCollectNum());
//    response.setCreateTime(navNodeDomain.getCreateTime());
//    response.setDesc(navNodeDomain.getDesc());
//    response.setFileSize(ConvertUtil.sizeFormatNum2String(navFileDomain.getFileSize()));
//    response.setDownloadUrl(
//        ossService.getPrivateFullUrl(privateBucketDomain, navFileDomain.getFileKey()));
//
//    return response;
//  }
//
//  @Override
//  public NavResponse saveUserFile(SaveFileRequest request) {
//    Long fileId = null;
//    if (null != request.getId()) {
//      NavNodeDomain navNodeDomain = navNodeService.getById(request.getId());
//      if (null != navNodeDomain) {
//        fileId = navNodeDomain.getResourceId();
//      }
//    }
//
//    Long userId = UserContext.getUserId();
//    String userUuid = UserContext.getUserUuid();
//
//    String targetKey = request.getFileKey();
//
//    if (null != fileId) {
//      final NavFileDomain navFileDomain = navFileService.getById(fileId);
//      final String fileKey = navFileDomain.getFileKey();
//      if (!StringUtils.equals(fileKey, request.getFileKey())) {
//        // 将临时目录的文件移动到对应的目录中
//        final Long pid = request.getPid();
//        final String nodeIdPath = navNodeService.getNodeIdPathById(pid);
//
//        targetKey = String.format("user/%s/file/%s/%s", userUuid, nodeIdPath, request.getFname());
//
//        try {
//          ossService.moveObject(privateBucket, request.getFileKey(), privateBucket, targetKey);
//        } catch (QiniuException e) {
//          log.error("移动文件异常", e);
//          throw new BusinessException(StatusCode.FAILED, "系统异常");
//        }
//      }
//    }
//
//    SaveFileParam saveFileParam = new SaveFileParam();
//    saveFileParam.setId(fileId);
//    saveFileParam.setFileKey(targetKey);
//    saveFileParam.setFileSize(request.getFsize());
//
//    fileId = navFileService.saveFile(saveFileParam);
//
//    SaveNodeParam saveNodeParam = new SaveNodeParam();
//    saveNodeParam.setId(request.getId());
//    saveNodeParam.setPid(request.getPid());
//    saveNodeParam.setName(request.getFname());
//    saveNodeParam.setUserId(userId);
//    saveNodeParam.setNodeType(DataTypeEnum.FILE.getDataType());
//    saveNodeParam.setResourceId(fileId);
//    saveNodeParam.setDesc(request.getDesc());
//
//    Long nodeId = navNodeService.saveUserNode(saveNodeParam);
//
//    NavNodeDomain navNodeDomain = navNodeService.getById(nodeId);
//    final NavFileDomain navFileDomain = navFileService.getById(fileId);
//
//    NavResponse otherLevelResponse =
//        this.getOtherLevelResponse(navNodeDomain, null, navFileDomain);
//
//    return otherLevelResponse;
//  }
//
//  @Override
//  public List<NavResponse> getRelationItems(Long id) {
//    final NavNodeDomain navNodeDomain = navNodeService.getById(id);
//    if (null == navNodeDomain) {
//      throw new BusinessException(StatusCode.FAILED, "数据不存在");
//    }
//
//    List<NavResponse> otherLevelItems = this.getOtherLevelItems(navNodeDomain.getPid());
//    otherLevelItems =
//        otherLevelItems.stream()
//            .filter(x -> !Objects.equals(x.getId(), id))
//            .collect(Collectors.toList());
//    return otherLevelItems;
//  }
//
//  @Override
//  @Transactional
//  public Boolean sortNode(Long targetNodeId, Long targetNextNodeId) {
//    Long userId = UserContext.getUserId();
//
//    final NavNodeDomain targetNextNodeDomain = navNodeService.getById(targetNextNodeId, userId);
//
//    navNodeService.updateNodeSort(targetNodeId, targetNextNodeDomain.getSort());
//
//    navNodeService.batchSetNodeSortPlusOne(
//        targetNextNodeDomain.getPid(), targetNextNodeDomain.getSort());
//
//    return true;
//  }
//
//  @Override
//  public Boolean changeNodeDir(Long targetNodeId, Long targetDirNodeId) {
//    final Long userId = UserContext.getUserId();
//    navNodeService.updateNodeDir(targetNodeId, targetDirNodeId, userId);
//    return true;
//  }
//}
