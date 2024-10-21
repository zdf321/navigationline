package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.core.TeamDeptService;
import net.xbookmark.dao.mapper.TeamDeptMapper;
import net.xbookmark.dao.model.TeamDeptEntity;
import org.springframework.stereotype.Service;

/**
 * @author zhangdingfei
 * @date 2023/7/19 22:00
 */
@Service
@Slf4j
public class TeamDeptServiceImpl extends ServiceImpl<TeamDeptMapper, TeamDeptEntity>
    implements TeamDeptService {

  @Override
  public void addTeamDept(String teamId, String name, String pid) {
    TeamDeptEntity entity = new TeamDeptEntity();
    entity.setTeamId(teamId);
    entity.setName(name);
    entity.setPid(pid);
    this.save(entity);
  }
}
