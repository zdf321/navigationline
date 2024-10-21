package net.xbookmark.common.service;

import net.xbookmark.core.domain.NavDatasDomain;
import net.xbookmark.dao.model.NavEntity;

import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/7/30 15:14
 */
public interface CommonService {

  NavDatasDomain convertFromNavList(List<NavEntity> navList, String uid, String teamId);
}
