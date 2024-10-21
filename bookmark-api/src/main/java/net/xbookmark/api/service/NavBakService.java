package net.xbookmark.api.service;

import net.xbookmark.api.nav.web.request.CollectRequest;
import net.xbookmark.api.nav.web.request.SaveFolderRequest;
import net.xbookmark.api.nav.web.request.SaveFileRequest;
import net.xbookmark.api.nav.web.request.SaveWebsiteRequest;
import net.xbookmark.api.nav.web.response.*;

import java.util.List;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/1 08:56
 */
public interface NavBakService {

  List<FirstLevelResponse> getFirstLevelFolders();

  List<SecondLevelResponse> getSecondLevelFolders(Long pid);

  List<SecondLevelResponse> getSecondLevelFoldersByPath(String path);

  List<NavResponse> getOtherLevelItems(Long pid);

  SecondLevelResponse addUserSecondLevelFolder();

  Boolean deleteUserNode(Long id, Boolean includeInnerNode);

  String updateUserSecondLevelFolder(Long id, String name);

  NavResponse saveUserWebsite(SaveWebsiteRequest request);

  NavResponse saveUserDir(SaveFolderRequest request);

  List<MyDirTreeResponse> getUserDirTree();

  WebsiteInfoResponse getWebsiteInfo(String url);

  Boolean collect(CollectRequest request);

  Boolean collectIndex(Long targetUserId, String targetUserUuid);

  WebsiteDetailResponse getWebsiteDetail(Long id);

  FileDetailResponse getFileDetail(Long id);

  NavResponse saveUserFile(SaveFileRequest request);

  List<NavResponse> getRelationItems(Long id);

  Boolean sortNode(Long targetNodeId, Long targetNextNodeId);

  Boolean changeNodeDir(Long targetNodeId, Long targetDirNodeId);
}
