package net.xbookmark.api.service;

import net.xbookmark.api.nav.web.request.*;
import net.xbookmark.api.nav.web.response.MyDataCountResponse;
import net.xbookmark.api.nav.web.response.MyDirTreeResponse;
import net.xbookmark.api.nav.web.response.WebsiteInfoResponse;
import net.xbookmark.core.domain.NavDatasDomain;

/**
 * @Author: zigui.zdf @Date: 2022/3/13 11:27:05 @Description:
 */
public interface MyDataService {

  Boolean saveFolder(SaveFolderRequest request, String uid);

  Boolean saveWebsite(SaveWebsiteRequest request, String uid);

  NavDatasDomain loadDatas(LoadMyDataRequest request, String uid);

  Boolean saveSort(SaveSortRequest request, String uid);

  Boolean move(MoveDataRequest request, String uid);

  Boolean clone(MoveDataRequest request, String uid);

  Boolean toTrash(RemoveDataRequest request, String uid);

  Boolean removeFromTrash(RemoveDataRequest request, String uid);

  Boolean removeAllFromTrash(String uid);

  Boolean restore(String id, String uid);

  WebsiteInfoResponse getWebsiteInfo(String url);

  MyDirTreeResponse getDirTree(String spaceId);

  MyDataCountResponse getFileCount(String teamId);
}
