package net.xbookmark.api.service;

import net.xbookmark.api.nav.web.response.TeamDirTreeResponse;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/8/20 15:53
 */
public interface TeamNavService {

  List<TeamDirTreeResponse> getDirTree(String uid, String teamId);

  List<TeamDirTreeResponse> getDirTree(String uid);
}
