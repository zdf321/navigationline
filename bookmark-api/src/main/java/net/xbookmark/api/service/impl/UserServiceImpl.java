package net.xbookmark.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.xbookmark.api.nav.web.request.LoginRequest;
import net.xbookmark.api.nav.web.request.RegisterRequest;
import net.xbookmark.api.nav.web.request.SaveUserInfoRequest;
import net.xbookmark.api.nav.web.request.UpdatePwdRequest;
import net.xbookmark.api.nav.web.response.UserInfoResponse;
import net.xbookmark.api.service.UserService;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.StatusCode;
import net.xbookmark.common.util.JwtUtil;
import net.xbookmark.common.vo.UserInfo;
import net.xbookmark.core.AccountService;
import net.xbookmark.core.FileService;
import net.xbookmark.core.TeamService;
import net.xbookmark.core.TeamSpaceService;
import net.xbookmark.core.domain.TeamInfoDomain;
import net.xbookmark.core.domain.TeamSpaceInfoDomain;
import net.xbookmark.dao.model.AccountEntity;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/1 20:49
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamSpaceService teamSpaceService;

    @Autowired
    private FileService fileService;

    @Override
    public Boolean register(RegisterRequest param, HttpServletRequest request) {

        final String account = param.getAccount();

        AccountEntity entity = new AccountEntity();
        LambdaQueryWrapper<AccountEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountEntity::getUsername, account);

        long count = accountService.count(queryWrapper);

        if (count > 0) {
            throw new BusinessException(StatusCode.FAILED, "该账号已被占用");
        }

        String salt = UUID.randomUUID().toString(true).substring(0, 16);
        String password = SecureUtil.md5(param.getPassword() + salt);

        entity.setUsername(account);
        entity.setPassword(password);
        entity.setSalt(salt);
        entity.setNickname("n" + RandomUtils.nextInt(10000, 99999));

        accountService.save(entity);

        // 自动生成个人空间
        teamService.addPersonalTeam(entity.getId());

        return true;
    }

    @Override
    public Boolean login(
            LoginRequest param, HttpServletRequest request, HttpServletResponse response) {
        AccountEntity entity = loginByAccount(param.getAccount(), param.getPassword());

        UserInfo userInfo = new UserInfo();
        userInfo.setUid(entity.getId());

        JwtUtil.refreshToken(userInfo, response);

        return true;
    }

    private AccountEntity loginByAccount(String account, String password) {

        LambdaQueryWrapper<AccountEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountEntity::getUsername, account);
        queryWrapper.last("limit 1");

        final AccountEntity entity = accountService.getOne(queryWrapper);

        if (entity == null) {
            throw new BusinessException(StatusCode.FAILED, "账号或密码错误");
        }

        String encryptPwd = SecureUtil.md5(password + entity.getSalt());
        if (!Objects.equals(encryptPwd, entity.getPassword())) {
            throw new BusinessException(StatusCode.FAILED, "用户名或密码错误");
        }

        return entity;
    }

    @Override
    public Boolean logout(HttpServletResponse response) {
        JwtUtil.deleteToken(response);
        return true;
    }

    @Override
    public UserInfoResponse getUserInfo(String uid) {
        final AccountEntity accountEntity = accountService.getById(uid);

        UserInfoResponse userInfoResponse =
                BeanUtil.copyProperties(accountEntity, UserInfoResponse.class);
        userInfoResponse.setUid(accountEntity.getId());
        userInfoResponse.setUsername(DesensitizedUtil.mobilePhone(accountEntity.getUsername()));
        userInfoResponse.setAvatar(fileService.getFilePrefilePath(accountEntity.getAvatar()));

        TeamInfoDomain personalTeamInfo = teamService.getPersonalTeamInfo(uid);
        TeamSpaceInfoDomain personalSpace =
                teamSpaceService.getPersonalSpace(personalTeamInfo.getTeamId(), uid);

        userInfoResponse.setMyTeamId(personalTeamInfo.getTeamId());
        userInfoResponse.setMyTeamSpaceId(personalSpace.getSpaceId());

        return userInfoResponse;
    }

    @Override
    public Boolean saveUserInfo(SaveUserInfoRequest request, String uid) {
        AccountEntity update = new AccountEntity();
        update.setId(uid);
        update.setNickname(request.getNickname());
        update.setIndustry(request.getIndustry());
        update.setCompany(request.getCompany());
        update.setProfession(request.getProfession());
        update.setEducation(request.getEducation());
        update.setRemark(request.getRemark());
        update.setAvatar(request.getAvatar());
        accountService.updateById(update);

        return true;
    }

    @Override
    public Boolean updatePassword(UpdatePwdRequest request, String uid) {
        AccountEntity accountEntity = accountService.getById(uid);

        String entryPwd = SecureUtil.md5(request.getOldPwd() + accountEntity.getSalt());
        if (!Objects.equals(entryPwd, accountEntity.getPassword())) {
            throw new BusinessException("旧密码错误");
        }

        String newEntryPwd = SecureUtil.md5(request.getNewPwd() + accountEntity.getSalt());
        accountEntity.setPassword(newEntryPwd);
        accountService.updateById(accountEntity);

        return true;
    }
}
