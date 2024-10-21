package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.core.AccountService;
import net.xbookmark.dao.mapper.AccountMapper;
import net.xbookmark.dao.model.AccountEntity;
import org.springframework.stereotype.Service;

/**
 * @author zhangdingfei
 * @date 2023/7/29 17:16
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountEntity>
    implements AccountService {}
