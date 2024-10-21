package net.xbookmark.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.xbookmark.common.domain.TeamMemberDomain;
import net.xbookmark.common.domain.TeamSpaceMemberDomain;
import net.xbookmark.dao.model.TeamMemberEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhangdingfei
 * @date 2023/7/19 21:32
 */
public interface TeamMemberMapper extends BaseMapper<TeamMemberEntity> {

  IPage<TeamSpaceMemberDomain> getSpaceMemberPage(
      IPage<TeamSpaceMemberDomain> page,
      @Param("teamId") String teamId,
      @Param("spaceId") String spaceId,
      @Param("name") String name);

  TeamSpaceMemberDomain getSpaceMember(
      @Param("teamId") String teamId, @Param("spaceId") String spaceId, @Param("uid") String uid);

  IPage<TeamMemberDomain> getTeamMemberPage(
      IPage<TeamMemberDomain> page, @Param("teamId") String teamId, @Param("name") String name);
}
