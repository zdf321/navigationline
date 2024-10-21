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
//import net.xbookmark.core.SystemToolNavService;
//import net.xbookmark.core.NavService;
//import net.xbookmark.dao.mapper.SystemToolNavMapper;
//import net.xbookmark.dao.model.SystemToolNavEntity;
//import net.xbookmark.dao.model.NavEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
///** @Author: zigui.zdf @Date: 2022/3/18 23:08:59 @Description: */
//@Service
//@Slf4j
//public class SystemToolNavServiceImpl implements SystemToolNavService {
//  @Autowired private SystemToolNavMapper systemToolNavMapper;
//
//  @Autowired private NavService navService;
//
//  @Autowired private OSSService ossService;
//
//  @Autowired private NavProperties navProperties;
//
//  @Override
//  public void clone(String id, String userNavFolderId, String uid) {
//    final SystemToolNavEntity systemToolNav = systemToolNavMapper.findById(id);
//
//    if (null == systemToolNav) {
//      throw new BusinessException(StatusCode.FAILED, "数据不存在");
//    }
//
//    final NavEntity userNav = navService.getById(userNavFolderId, uid);
//
//    if (null == userNav) {
//      throw new BusinessException(StatusCode.FAILED, "数据不存在");
//    }
//
//    cloneWebsite(systemToolNav, userNavFolderId, uid);
//  }
//
//  private void cloneWebsite(SystemToolNavEntity systemToolNav, String pid, String uid) {
//    final double nextSort = navService.getNextSortByPid(pid, uid);
//
//    NavEntity userNav = new NavEntity();
//    userNav.setPid(pid);
//    userNav.setName(systemToolNav.getName());
//    userNav.setDesc(systemToolNav.getDesc());
//    userNav.setDataType(DataTypeEnum.WEBSITE.getDataType());
//    userNav.setUid(uid);
//    userNav.setSort(nextSort);
//    userNav.setRowState(1);
//
//    NavEntity.Website website = new NavEntity.Website();
//    website.setUrl(systemToolNav.getUrl());
//
//    Map<String, String> keyMap = new HashMap<>();
//
//    final String logoKey = systemToolNav.getLogoKey();
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
//}
