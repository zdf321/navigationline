package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.dao.model.TeamInviteInfoEntity;
import net.xbookmark.core.TeamInviteInfoService;
import net.xbookmark.dao.mapper.TeamInviteInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【tb_team_invite_info】的数据库操作Service实现
* @createDate 2023-09-04 13:25:07
*/
@Service
public class TeamInviteInfoServiceImpl extends ServiceImpl<TeamInviteInfoMapper, TeamInviteInfoEntity>
    implements TeamInviteInfoService{

}




