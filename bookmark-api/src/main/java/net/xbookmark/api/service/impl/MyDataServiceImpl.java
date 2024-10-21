package net.xbookmark.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.api.nav.web.request.*;
import net.xbookmark.api.nav.web.response.MyDataCountResponse;
import net.xbookmark.api.nav.web.response.MyDirTreeResponse;
import net.xbookmark.api.nav.web.response.WebsiteInfoResponse;
import net.xbookmark.api.service.MyDataService;
import net.xbookmark.common.enums.DataTypeEnum;
import net.xbookmark.common.enums.DelFlagEnum;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.service.CommonService;
import net.xbookmark.common.util.Constants;
import net.xbookmark.common.util.ConvertUtil;
import net.xbookmark.config.NavProperties;
import net.xbookmark.core.*;
import net.xbookmark.core.domain.FetchWebsiteInfoDomain;
import net.xbookmark.core.domain.NavDatasDomain;
import net.xbookmark.dao.model.NavEntity;
import net.xbookmark.dao.model.NavWebsiteEntity;
import net.xbookmark.dao.model.TeamSpaceEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: zigui.zdf @Date: 2022/3/13 11:28:33 @Description:
 */
@Service
@Slf4j
public class MyDataServiceImpl implements MyDataService {

    @Autowired
    private NavService navService;

    @Autowired
    private NavWebsiteService navWebsiteService;

    @Autowired
    private NavProperties navProperties;

    @Autowired
    private VIPService vipService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TeamSpaceService teamSpaceService;

    @Autowired
    private TeamSpaceMemberService teamSpaceMemberService;

    @Override
    public Boolean saveFolder(SaveFolderRequest request, String uid) {
        if (StringUtils.isBlank(request.getId())) {
            String spaceId = request.getSpaceId();

            if (StringUtils.isBlank(spaceId)) {
                NavEntity navEntity = navService.getById(request.getPid());
                spaceId = navEntity.getSpaceId();
            }

            teamSpaceMemberService.checkSpaceEditAuth(spaceId, uid);

            TeamSpaceEntity teamSpace = teamSpaceService.getById(spaceId);

            final int nextSortByPid = navService.getNextSortByPid(request.getPid(), spaceId, uid);

            NavEntity entity = new NavEntity();
            entity.setUid(uid);
            entity.setPid(request.getPid());
            entity.setSpaceId(spaceId);
            entity.setTeamId(teamSpace.getTeamId());
            entity.setDataType(DataTypeEnum.DIR.getDataType());
            entity.setName(request.getName());
            entity.setDesc(request.getDesc());
            entity.setSort(nextSortByPid);
            entity.setDelFlag(DelFlagEnum.ENABLE.getCode());
            navService.save(entity);
            return true;
        }

        NavEntity entity = navService.getById(request.getId());

        teamSpaceMemberService.checkSpaceEditAuth(entity.getSpaceId(), uid);

        entity.setName(request.getName());
        entity.setDesc(request.getDesc());
        if (!StringUtils.equals(entity.getPid(), request.getPid())) {
            final int nextSortByPid =
                    navService.getNextSortByPid(request.getPid(), entity.getSpaceId(), uid);
            entity.setPid(request.getPid());
            entity.setSort(nextSortByPid);
        }

        navService.updateById(entity);

        return true;
    }

    @Override
    @Transactional
    public Boolean saveWebsite(SaveWebsiteRequest request, String uid) {
        String logoKey = request.getLogoUrl();
        if (StringUtils.startsWith(logoKey, Constants.FileKey.FILE_PREVIEW_PATH)) {
            logoKey = logoKey.substring(Constants.FileKey.FILE_PREVIEW_PATH.length());
        }

        // 新增
        if (StringUtils.isBlank(request.getId())) {
            navService.checkFileCount(uid);

            String spaceId = request.getSpaceId();

            if (StringUtils.isBlank(spaceId)) {
                NavEntity navEntity = navService.getById(request.getPid());
                spaceId = navEntity.getSpaceId();
            }

            teamSpaceMemberService.checkSpaceEditAuth(spaceId, uid);

            TeamSpaceEntity teamSpace = teamSpaceService.getById(spaceId);

            final int nextSort = navService.getNextSortByPid(request.getPid(), spaceId, uid);

            NavEntity entity = new NavEntity();
            entity.setPid(request.getPid());
            entity.setName(request.getName());
            entity.setSpaceId(spaceId);
            entity.setTeamId(teamSpace.getTeamId());
            entity.setDesc(request.getDesc());
            entity.setDataType(DataTypeEnum.WEBSITE.getDataType());
            entity.setUid(uid);
            entity.setSort(nextSort);
            entity.setDelFlag(DelFlagEnum.ENABLE.getCode());
            navService.save(entity);

            NavWebsiteEntity website = new NavWebsiteEntity();
            website.setUrl(request.getUrl());
            website.setLogoKey(logoKey);
            website.setNavId(entity.getId());
            navWebsiteService.save(website);

            vipService.increaseUserFileCount(uid);

            return true;
        }

        // 编辑
        NavEntity entity = navService.getById(request.getId());

        teamSpaceMemberService.checkSpaceEditAuth(entity.getSpaceId(), uid);

        entity.setName(request.getName());
        entity.setDesc(request.getDesc());
        if (!StringUtils.equals(entity.getPid(), request.getPid())) {
            final int nextSortByPid =
                    navService.getNextSortByPid(request.getPid(), entity.getSpaceId(), uid);
            entity.setPid(request.getPid());
            entity.setSort(nextSortByPid);
        }
        navService.updateById(entity);

        NavWebsiteEntity website = new NavWebsiteEntity();
        website.setUrl(request.getUrl());
        website.setLogoKey(logoKey);

        navWebsiteService.update(
                website,
                new LambdaQueryWrapper<NavWebsiteEntity>().eq(NavWebsiteEntity::getNavId, request.getId()));

        return true;
    }

    @Override
    public NavDatasDomain loadDatas(LoadMyDataRequest request, String uid) {
        LambdaQueryWrapper<NavEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NavEntity::getSpaceId, request.getSpaceId());

        if ("trash".equals(request.getResource())) {
            queryWrapper.eq(NavEntity::getDelFlag, DelFlagEnum.DISABLE.getCode());
        } else {
            queryWrapper.eq(NavEntity::getDelFlag, DelFlagEnum.ENABLE.getCode());
        }

        if ("data".equals(request.getResource())) {
            queryWrapper.eq(NavEntity::getPid, request.getPid());
        }

        final String search = request.getSearch();

        if (StringUtils.isNotBlank(search)) {
            queryWrapper.and(
                    w -> w.like(NavEntity::getName, search).or().like(NavEntity::getDesc, search));
        }

        final Integer sortType = request.getSortType();

        if (sortType == 1) {
            queryWrapper.orderByDesc(NavEntity::getCreateTime);
        } else if (sortType == 2) {
            queryWrapper.orderByDesc(NavEntity::getUpdateTime);
        } else if (sortType == 3) {
            queryWrapper.orderByAsc(NavEntity::getName);
        } else if (sortType == 4) {
            queryWrapper.orderByAsc(NavEntity::getDataType);
        } else {
            queryWrapper.orderByAsc(NavEntity::getSort);
        }

        // 最近修改，强制指定排序
        if ("history".equals(request.getResource())) {
            queryWrapper.orderByDesc(NavEntity::getUpdateTime);
        }

        int pageSize = 1000;

        Page<NavEntity> page =
                navService.page(new Page<>(request.getPageNum(), pageSize), queryWrapper);

        final List<NavEntity> records = page.getRecords();

        TeamSpaceEntity teamSpaceEntity = teamSpaceService.getById(request.getSpaceId());
        if (null == teamSpaceEntity) {
            throw new BusinessException("空间不存在");
        }

        NavDatasDomain response =
                commonService.convertFromNavList(records, uid, teamSpaceEntity.getTeamId());
        response.setSortType(request.getSortType());

        return response;
    }

    @Override
    public Boolean saveSort(SaveSortRequest request, String uid) {
        final NavEntity userNav = navService.getById(request.getId(), uid);
        userNav.setSort(request.getSortNum());
        navService.updateById(userNav);
        return true;
    }

    @Override
    public Boolean move(MoveDataRequest request, String uid) {
        final List<NavEntity> entityList = navService.getByIds(request.getDataIds());

        if (CollectionUtils.isEmpty(entityList)) {
            return true;
        }

        String spaceId = request.getSpaceId();
        if (StringUtils.isBlank(request.getSpaceId())) {
            spaceId = navService.getById(request.getFolderId()).getSpaceId();
        }

        teamSpaceMemberService.checkSpaceEditAuth(spaceId, uid);

        for (NavEntity entity : entityList) {
            entity.setPid(request.getFolderId());
            entity.setSpaceId(spaceId);
            navService.updateById(entity);
        }

        return true;
    }

    @Override
    public Boolean clone(MoveDataRequest request, String uid) {
        final List<NavEntity> entityList = navService.getByIds(request.getDataIds());

        if (CollectionUtils.isEmpty(entityList)) {
            return true;
        }

        navService.checkFileCount(uid);

        String spaceId = request.getSpaceId();
        if (StringUtils.isBlank(request.getSpaceId())) {
            spaceId = navService.getById(request.getFolderId()).getSpaceId();
        }

        teamSpaceMemberService.checkSpaceEditAuth(spaceId, uid);

        for (NavEntity entity : entityList) {
            navService.clone(entity.getId(), request.getFolderId(), spaceId, uid);
            navService.increseCollectNum(entity.getId());
        }

        return true;
    }

    @Override
    @Transactional
    public Boolean toTrash(RemoveDataRequest request, String uid) {
        final List<NavEntity> entityList = navService.getByIds(request.getIds());

        if (CollectionUtils.isEmpty(entityList)) {
            return true;
        }

        for (NavEntity navEntity : entityList) {
            teamSpaceMemberService.checkSpaceEditAuth(navEntity.getSpaceId(), uid);

            navEntity.setDelFlag(DelFlagEnum.DISABLE.getCode());
            navService.updateById(navEntity);
        }

        return true;
    }

    @Override
    public Boolean removeFromTrash(RemoveDataRequest request, String uid) {
        navService.delete(request.getIds(), uid);
        return true;
    }

    @Override
    public Boolean removeAllFromTrash(String uid) {
        List<NavEntity> list =
                navService.list(
                        new LambdaQueryWrapper<NavEntity>()
                                .eq(NavEntity::getDelFlag, "1")
                                .eq(NavEntity::getUid, uid));

        final List<String> ids = list.stream().map(NavEntity::getId).collect(Collectors.toList());

        navService.removeBatchByIds(ids);

        return true;
    }

    @Override
    public Boolean restore(String id, String uid) {
        final NavEntity userNav = navService.getById(id, uid);

        teamSpaceMemberService.checkSpaceEditAuth(userNav.getSpaceId(), uid);

        if (Objects.equals(userNav.getDelFlag(), "1")) {
            NavEntity entity = new NavEntity();
            entity.setId(id);
            entity.setDelFlag("0");
            navService.updateById(entity);
        }
        return true;
    }

    @Override
    public WebsiteInfoResponse getWebsiteInfo(String url) {
        FetchWebsiteInfoDomain fetchWebsiteInfoDomain = null;
        try {
            fetchWebsiteInfoDomain = navService.fetchWebsiteInfo(url);
        } catch (Exception e) {
            log.error("网站信息抓取时异常", e);
        }

        return ConvertUtil.copyProperties(fetchWebsiteInfoDomain, WebsiteInfoResponse.class);
    }

    @Override
    public MyDirTreeResponse getDirTree(String spaceId) {
        List<NavEntity> multi =
                navService.list(
                        new LambdaQueryWrapper<NavEntity>()
                                .eq(NavEntity::getSpaceId, spaceId)
                                .eq(NavEntity::getDataType, DataTypeEnum.DIR.getDataType())
                                .eq(NavEntity::getDelFlag, 0)
                                .orderByAsc(NavEntity::getSort));

        TeamSpaceEntity spaceEntity = teamSpaceService.getById(spaceId);

        MyDirTreeResponse response = new MyDirTreeResponse();
        response.setName(spaceEntity.getName());
        response.setId("root");
        setChildren(response, multi);

        return response;
    }

    @Override
    public MyDataCountResponse getFileCount(String teamId) {

        final long count = navService.getFileCount(teamId);
        final Integer fileMaxCountVisitor = navProperties.getSystem().getFileMaxCountVisitor();

        return new MyDataCountResponse(count, fileMaxCountVisitor);
    }

    private void setChildren(MyDirTreeResponse parent, List<NavEntity> multi) {
        final List<NavEntity> childs =
                multi.stream().filter(e -> e.getPid().equals(parent.getId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childs)) {
            return;
        }

        List<MyDirTreeResponse> children = new ArrayList<>();

        for (NavEntity child : childs) {
            MyDirTreeResponse c = new MyDirTreeResponse();
            c.setId(child.getId());
            c.setName(child.getName());
            setChildren(c, multi);
            children.add(c);
        }
        parent.setChildren(children);
    }
}
