package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.core.OauthAccountService;
import net.xbookmark.dao.mapper.OauthAccountMapper;
import net.xbookmark.dao.model.OauthAccountEntity;
import org.springframework.stereotype.Service;

/**
 * @author zhangdingfei
 * @date 2023/7/29 21:17
 */
@Service
public class OauthAccountServiceImpl extends ServiceImpl<OauthAccountMapper, OauthAccountEntity>
    implements OauthAccountService {}
