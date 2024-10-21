package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.common.response.TableDataInfo;
import net.xbookmark.core.domain.FetchWebsiteInfoDomain;
import net.xbookmark.core.domain.NavDataItemDomain;
import net.xbookmark.core.domain.NavDatasDomain;
import net.xbookmark.core.domain.WebsiteDetailDomain;
import net.xbookmark.dao.model.NavEntity;

import java.util.Collection;
import java.util.List;

/**
 * @Author: zigui.zdf @Date: 2022/3/13 11:52:38 @Description:
 */
public interface NavService extends IService<NavEntity> {

  int getNextSortByPid(String pid, String spaceId, String uid);

  NavEntity getById(String id, String uid);

  List<NavEntity> getByIds(Collection<String> ids);

  FetchWebsiteInfoDomain fetchWebsiteInfo(String url);

  void clone(String id, String pid, String spaceId, String uid);

  void delete(String id, String uid);

  void delete(Collection<String> ids, String uid);

  void insertOne(NavEntity entity);

  long getFileCount(String teamId);

  void checkFileCount(String uid);

  NavDatasDomain getBySpaceId(String spaceId);

  NavDatasDomain getByFolderId(String folderId);

  TableDataInfo<NavDataItemDomain> searchTeamPublishDatasPage(
      String publishId, String search, Integer pageNo, Integer pageSize);

  WebsiteDetailDomain getWebsiteDetail(String publishId, String id);

  void increseHitsNum(String id);

  void increseCollectNum(String id);
}
