package net.xbookmark.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.xbookmark.common.domain.SpaceRoleDomain;
import net.xbookmark.dao.model.TeamSpaceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zigui.zdf @Date: 2022/3/13 11:49:19 @Description:
 */
public interface TeamSpaceMapper extends BaseMapper<TeamSpaceEntity> {

  List<SpaceRoleDomain> selectSpaceRoleInfo(
      @Param("teamId") String teamId, @Param("uid") String uid);
}
