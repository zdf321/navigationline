package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.core.domain.TeamPublishDataDomain;
import net.xbookmark.dao.model.TeamPublishInfoEntity;

/**
 * @author DELL
 * @description 针对表【tb_team_publish_info(团队发布信息)】的数据库操作Service
 * @createDate 2023-10-09 21:48:31
 */
public interface TeamPublishInfoService extends IService<TeamPublishInfoEntity> {

  void checkNeedLogin(String publishId);

  TeamPublishInfoEntity setTeamPublishInfo(
      String teamId,
      Boolean published,
      Boolean showAd,
      Boolean checkAuth,
      Boolean needLogin,
      String uid);

  TeamPublishInfoEntity getTeamPublishInfo(String teamId);

  String generateDomain();

  TeamPublishDataDomain getTeamPublishDatas(String publishId);
}
