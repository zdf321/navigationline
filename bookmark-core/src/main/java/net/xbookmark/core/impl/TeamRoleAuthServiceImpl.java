package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.dao.model.TeamRoleAuthEntity;
import net.xbookmark.core.TeamRoleAuthService;
import net.xbookmark.dao.mapper.TeamRoleAuthEntityMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【tb_team_role_auth(团队角色权限配置)】的数据库操作Service实现
* @createDate 2023-08-02 22:36:26
*/
@Service
public class TeamRoleAuthServiceImpl extends ServiceImpl<TeamRoleAuthEntityMapper, TeamRoleAuthEntity>
    implements TeamRoleAuthService {

}




