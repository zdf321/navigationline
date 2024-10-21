//package net.xbookmark.core.impl;
//
//import cn.hutool.core.util.IdUtil;
//import com.qiniu.common.QiniuException;
//import lombok.extern.slf4j.Slf4j;
//import net.xbookmark.common.enums.DataTypeEnum;
//import net.xbookmark.common.exception.BusinessException;
//import net.xbookmark.common.response.StatusCode;
//import net.xbookmark.common.util.Constants;
//import net.xbookmark.config.NavProperties;
//import net.xbookmark.core.OSSService;
//import net.xbookmark.core.SystemNavService;
//import net.xbookmark.core.NavService;
//import net.xbookmark.dao.mapper.SystemNavMapper;
//import net.xbookmark.dao.model.SystemNavEntity;
//import net.xbookmark.dao.model.NavEntity;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///** @Author: zigui.zdf @Date: 2022/3/18 23:08:59 @Description: */
//@Service
//@Slf4j
//public class SystemNavServiceImpl implements SystemNavService {
//  @Autowired private SystemNavMapper systemNavMapper;
//
//  @Autowired private NavService navService;
//
//  @Autowired private OSSService ossService;
//
//  @Autowired private NavProperties navProperties;
//
//  @Override
//  public void clone(String id, String userNavFolderId, String uid) {
//    final SystemNavEntity systemNav = systemNavMapper.findById(id);
//
//    if (null == systemNav) {
//      throw new BusinessException(StatusCode.FAILED, "数据不存在");
//    }
//
//    if (!StringUtils.equals("root", userNavFolderId)) {
//      final NavEntity userNav = navService.getById(userNavFolderId, uid);
//
//      if (null == userNav) {
//        throw new BusinessException(StatusCode.FAILED, "数据不存在");
//      }
//    }
//
//    final Integer dataType = systemNav.getDataType();
//    final DataTypeEnum dataTypeEnum = DataTypeEnum.of(dataType);
//
//    switch (dataTypeEnum) {
//      case DIR:
//        cloneDir(systemNav, userNavFolderId, uid);
//        break;
//      case WEBSITE:
//        cloneWebsite(systemNav, userNavFolderId, uid);
//        break;
//      case FILE:
//        cloneFile(systemNav, userNavFolderId, uid);
//        break;
//    }
//  }
//
//  @Override
//  public void increaseCollectNum(String id) {
//
//    systemNavMapper.increaseCollectNum(id);
//  }
//
//  @Override
//  public void increaseHitNum(String id) {
//    systemNavMapper.increaseHitsNum(id);
//  }
//
//  private void cloneDir(SystemNavEntity systemNav, String pid, String uid) {
//    final double nextSort = navService.getNextSortByPid(pid, uid);
//
//    NavEntity userNav = new NavEntity();
//    userNav.setPid(pid);
//    userNav.setName(systemNav.getName());
//    userNav.setDesc(systemNav.getDesc());
//    userNav.setDataType(systemNav.getDataType());
//    userNav.setUid(uid);
//    userNav.setSort(nextSort);
//    userNav.setRowState(1);
//    navService.insertOne(userNav);
//
//    SystemNavEntity query = new SystemNavEntity();
//    query.setPid(systemNav.getId());
//    final List<SystemNavEntity> list = systemNavMapper.findMultiTkSelectStyle(query);
//
//    for (SystemNavEntity entity : list) {
//      final Integer dataType = entity.getDataType();
//      final DataTypeEnum dataTypeEnum = DataTypeEnum.of(dataType);
//      switch (dataTypeEnum) {
//        case DIR:
//          cloneDir(entity, userNav.getId(), uid);
//          break;
//        case WEBSITE:
//          cloneWebsite(entity, userNav.getId(), uid);
//          break;
//        case FILE:
//          cloneFile(entity, userNav.getId(), uid);
//          break;
//      }
//    }
//  }
//
//  private void cloneWebsite(SystemNavEntity systemNav, String pid, String uid) {
//    final double nextSort = navService.getNextSortByPid(pid, uid);
//
//    NavEntity userNav = new NavEntity();
//    userNav.setPid(pid);
//    userNav.setName(systemNav.getName());
//    userNav.setDesc(systemNav.getDesc());
//    userNav.setDataType(systemNav.getDataType());
//    userNav.setUid(uid);
//    userNav.setSort(nextSort);
//    userNav.setRowState(1);
//
//    NavEntity.Website website = new NavEntity.Website();
//    website.setUrl(systemNav.getWebsite().getUrl());
//
//    Map<String, String> keyMap = new HashMap<>();
//
//    final String logoKey = systemNav.getWebsite().getLogoKey();
//    String suffix = "";
//    if (logoKey.contains(".")) {
//      suffix = logoKey.substring(logoKey.lastIndexOf("."));
//    }
//    String prefix = String.format(Constants.OSSKey.USER_WEBSITE_LOGO_PREFIX, uid);
//    String newLogoKey = String.format("%s/%s%s", prefix, IdUtil.fastUUID(), suffix);
//    keyMap.put(logoKey, newLogoKey);
//
//    try {
//      ossService.copyObject(keyMap, navProperties.getQiniu().getBucketPublic());
//    } catch (QiniuException e) {
//      log.error(e.getMessage(), e);
//    }
//
//    website.setLogoKey(newLogoKey);
//    userNav.setWebsite(website);
//
//    navService.insertOne(userNav);
//  }
//
//  private void cloneFile(SystemNavEntity systemNav, String pid, String uid) {
//    // TODO: 2022/3/18 克隆文件
//  }
//}
