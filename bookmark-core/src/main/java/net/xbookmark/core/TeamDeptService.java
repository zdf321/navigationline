package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.dao.model.TeamDeptEntity;

/**
 * @author zhangdingfei
 * @date 2023/7/19 21:58
 */
public interface TeamDeptService extends IService<TeamDeptEntity> {

  void addTeamDept(String teamId, String name, String pid);
}
