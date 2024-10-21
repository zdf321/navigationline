package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.dao.model.AuthEntity;
import net.xbookmark.core.AuthService;
import net.xbookmark.dao.mapper.AuthEntityMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【tb_auth(权限点表)】的数据库操作Service实现
* @createDate 2023-08-02 22:36:26
*/
@Service
public class AuthServiceImpl extends ServiceImpl<AuthEntityMapper, AuthEntity>
    implements AuthService {

}




