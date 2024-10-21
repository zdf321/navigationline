package net.xbookmark.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.xbookmark.common.enums.TeamTypeEnum;
import net.xbookmark.dao.model.TeamEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author: zigui.zdf @Date: 2022/3/13 11:49:19 @Description:
 */
@Repository
public interface TeamMapper extends BaseMapper<TeamEntity> {

  default TeamEntity getPersonalTeamByUid(String uid) {
    TeamEntity teamEntity =
        this.selectOne(
            new LambdaQueryWrapper<TeamEntity>()
                .eq(TeamEntity::getUid, uid)
                .eq(TeamEntity::getTeamType, TeamTypeEnum.PERSONAL.name())
                .last("limit 1"));
    return teamEntity;
  }
}
