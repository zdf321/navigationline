package net.xbookmark.core.impl;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.enums.DataTypeEnum;
import net.xbookmark.common.enums.DelFlagEnum;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.StatusCode;
import net.xbookmark.common.response.TableDataInfo;
import net.xbookmark.common.service.CommonService;
import net.xbookmark.common.util.Constants;
import net.xbookmark.common.util.HtmlUtil;
import net.xbookmark.common.util.ImgUtil;
import net.xbookmark.common.util.UserContext;
import net.xbookmark.config.NavProperties;
import net.xbookmark.core.*;
import net.xbookmark.core.domain.*;
import net.xbookmark.dao.mapper.NavMapper;
import net.xbookmark.dao.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: zigui.zdf @Date: 2022/3/13 11:54:12 @Description:
 */
@Service
@Slf4j
public class NavServiceImpl extends ServiceImpl<NavMapper, NavEntity> implements NavService {

    @Autowired
    private FileService fileService;

    @Autowired
    private NavProperties navProperties;

    @Autowired
    private NavWebsiteService navWebsiteService;

    @Autowired
    private NavCommonUseService navCommonUseService;

    @Autowired
    private NavOpenHistoryService navOpenHistoryService;

    @Autowired
    private TeamSpaceMemberService teamSpaceMemberService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TeamPublishInfoService teamPublishInfoService;

    @Autowired
    private TeamSpaceService teamSpaceService;

    @Override
    public int getNextSortByPid(String pid, String spaceId, String uid) {
        NavEntity entity = this.getOne(new LambdaQueryWrapper<NavEntity>().eq(NavEntity::getPid, pid).eq(NavEntity::getUid, uid).eq(NavEntity::getSpaceId, spaceId).orderByDesc(NavEntity::getSort).last("limit 1"));

        if (null == entity) {
            return 1;
        }

        return entity.getSort() + 1;
    }

    @Override
    public NavEntity getById(String id, String uid) {
        final NavEntity userNav = this.getById(id);
        if (null == userNav) {
            throw new BusinessException(StatusCode.FAILED, "数据不存在");
        }
        if (!StringUtils.equals(userNav.getUid(), uid)) {
            throw new BusinessException(StatusCode.FAILED, "数据不存在");
        }
        return userNav;
    }

    @Override
    public List<NavEntity> getByIds(Collection<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<NavEntity> list = this.list(new LambdaQueryWrapper<NavEntity>().in(NavEntity::getId, ids));

        return list;
    }

    @Override
    public FetchWebsiteInfoDomain fetchWebsiteInfo(String url) {
        HttpResponse httpResponse = HttpRequest.get(url).timeout(4000).execute();
        if (httpResponse.getStatus() != 200) {
            return null;
        }

        String html = httpResponse.body();

        FetchWebsiteInfoDomain domain = new FetchWebsiteInfoDomain();

        if (StringUtils.isNotBlank(html)) {
            String title = HtmlUtil.getTitle(html);
            String description = HtmlUtil.getDescription(html);
            final String keywords = HtmlUtil.getKeywords(html);
            String icon = HtmlUtil.getIcon(html);

            if (StringUtils.isNotBlank(icon)) {
                if (!StringUtils.startsWithIgnoreCase(icon, "http")) {
                    UrlBuilder urlBuilder = UrlBuilder.ofHttp(url, CharsetUtil.CHARSET_UTF_8);

                    if (StringUtils.startsWithIgnoreCase(icon, "//")) {
                        icon = urlBuilder.getScheme() + ":" + icon;
                    } else {
                        icon = urlBuilder.getSchemeWithDefault() + "://" + urlBuilder.getHost() + icon;
                    }
                }

                String prefix = "image/site/logo";

                String base64 = ImgUtil.encodeNetImg(icon);
                String fileKey = fileService.uploadBase64(base64, prefix);

                domain.setLogoUrl(fileService.getFilePrefilePath(fileKey));
            }
            domain.setDescription(description);
            domain.setKeywords(keywords);
            domain.setTitle(title);

            return domain;
        }
        return null;
    }

    @Override
    public void clone(String id, String pid, String spaceId, String uid) {
        final NavEntity entity = this.getById(id);

        if (null == entity) {
            return;
        }

        final Integer dataType = entity.getDataType();
        final DataTypeEnum dataTypeEnum = DataTypeEnum.of(dataType);

        switch (dataTypeEnum) {
            case DIR:
                cloneFolder(entity, pid, spaceId, uid);
                break;
            case WEBSITE:
                cloneWebsite(entity, pid, spaceId, uid);
                break;
            case FILE:
                cloneFile(entity, pid, spaceId, uid);
                break;
        }
    }

    private void cloneFolder(NavEntity entity, String pid, String spaceId, String uid) {
        final List<NavEntity> children = this.list(new LambdaQueryWrapper<NavEntity>().eq(NavEntity::getUid, uid).eq(NavEntity::getPid, pid));

        final int nextSortByPid = this.getNextSortByPid(pid, spaceId, uid);

        entity.setId(null);
        entity.setPid(pid);
        entity.setSpaceId(spaceId);
        entity.setName(entity.getName() + " 副本");
        entity.setSort(nextSortByPid);
        this.save(entity);

        if (CollectionUtils.isNotEmpty(children)) {
            for (NavEntity child : children) {
                final DataTypeEnum dataTypeEnum = DataTypeEnum.of(child.getDataType());
                switch (dataTypeEnum) {
                    case DIR:
                        cloneFolder(child, entity.getId(), spaceId, uid);
                        break;
                    case WEBSITE:
                        cloneWebsite(child, entity.getId(), spaceId, uid);
                        break;
                    case FILE:
                        cloneFile(child, entity.getId(), spaceId, uid);
                        break;
                }
            }
        }
    }

    private void cloneWebsite(NavEntity entity, String pid, String spaceId, String uid) {
        NavWebsiteEntity website = navWebsiteService.getByNavId(entity.getId());
        if (null == website) {
            return;
        }
        final String logoKey = website.getLogoKey();
        if (StringUtils.isNotBlank(logoKey)) {
            String prefix = String.format(Constants.FileKey.USER_WEBSITE_LOGO_PREFIX, uid);

            website.setLogoKey(fileService.copyFile(logoKey, prefix));
        }

        final int nextSortByPid = getNextSortByPid(pid, spaceId, uid);

        entity.setId(null);
        entity.setPid(pid);
        entity.setSpaceId(spaceId);
        entity.setSort(nextSortByPid);
        entity.setName(entity.getName() + " 副本");
        this.save(entity);

        website.setId(null);
        website.setNavId(entity.getId());
        navWebsiteService.save(website);
    }

    private void cloneFile(NavEntity entity, String pid, String spaceId, String uid) {
        // TODO: 2024/3/19 克隆文件待实现
    }

    @Override
    public void delete(String id, String uid) {
        delete(Lists.newArrayList(id), uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Collection<String> ids, String uid) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        final List<NavEntity> list = getByIds(ids);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (NavEntity navEntity : list) {
            teamSpaceMemberService.checkSpaceEditAuth(navEntity.getSpaceId(), uid);

            DataTypeEnum dataTypeEnum = DataTypeEnum.of(navEntity.getDataType());
            switch (dataTypeEnum) {
                case DIR:
                    deleteFolder(navEntity.getId());
                    break;
                case WEBSITE:
                    deleteWebsite(navEntity.getId());
                    break;
                case FILE:
                    deleteFile(navEntity.getId());
                    break;
            }
        }
    }

    @Override
    public void insertOne(NavEntity entity) {
        this.save(entity);
    }

    @Override
    public long getFileCount(String teamId) {
        long count = this.count(new LambdaQueryWrapper<NavEntity>().eq(NavEntity::getTeamId, teamId).in(NavEntity::getDataType, DataTypeEnum.getFileTypes()));

        return count;
    }

    @Override
    public void checkFileCount(String uid) {
        //    final UserInfo userInfo = UserContext.getUserInfo();
        //    if (userInfo.isVIP()) {
        //      return;
        //    }
        //    final long userFileCount = vipService.getUserFileCount(uid);
        //
        //    final Integer fileMaxCountVisitor = navProperties.getSystem().getFileMaxCountVisitor();
        //
        //    if (userFileCount >= fileMaxCountVisitor) {
        //      throw new BusinessException(StatusCode.NO_FILE_COUNT_LEFT, "您的文件数量不足，无法创建新的文件");
        //    }
    }

    @Override
    public NavDatasDomain getBySpaceId(String spaceId) {
        NavDatasDomain domain = new NavDatasDomain();

        List<NavEntity> entityList = this.list(new LambdaQueryWrapper<NavEntity>().eq(NavEntity::getSpaceId, spaceId).eq(NavEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()).eq(NavEntity::getPid, "root").orderByAsc(NavEntity::getSort));

        if (CollectionUtils.isEmpty(entityList)) {
            return domain;
        }

        domain = commonService.convertFromNavList(entityList, UserContext.getUid(), entityList.get(0).getTeamId());

        return domain;
    }

    @Override
    public NavDatasDomain getByFolderId(String folderId) {
        NavDatasDomain domain = new NavDatasDomain();

        List<NavEntity> entityList = this.list(new LambdaQueryWrapper<NavEntity>().eq(NavEntity::getPid, folderId).eq(NavEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()).orderByAsc(NavEntity::getSort));

        if (CollectionUtils.isEmpty(entityList)) {
            return domain;
        }

        domain = commonService.convertFromNavList(entityList, UserContext.getUid(), entityList.get(0).getTeamId());

        return domain;
    }

    @Override
    public TableDataInfo<NavDataItemDomain> searchTeamPublishDatasPage(String publishId, String search, Integer pageNo, Integer pageSize) {

        TeamPublishInfoEntity publishInfo = teamPublishInfoService.getById(publishId);
        if (null == publishInfo) {
            return TableDataInfo.build();
        }

        String teamId = publishInfo.getTeamId();
        Boolean needLogin = Optional.ofNullable(publishInfo.getNeedLogin()).orElse(false);
        Boolean checkAuth = Optional.ofNullable(publishInfo.getCheckAuth()).orElse(false);

        String uid = UserContext.getUid();

        if (needLogin || checkAuth) {
            if (StringUtils.isBlank(uid)) {
                throw new BusinessException(StatusCode.NO_LOGIN, "请先登录");
            }
        }

        List<TeamSpaceInfoDomain> teamSpaceList = checkAuth ? teamSpaceService.getTeamSpaceList(teamId, uid) : teamSpaceService.getTeamSpaceList(teamId);
        if (CollectionUtils.isEmpty(teamSpaceList)) {
            return TableDataInfo.build();
        }

        List<String> spaceIds = teamSpaceList.stream().map(TeamSpaceInfoDomain::getSpaceId).collect(Collectors.toList());

        LambdaQueryWrapper<NavEntity> queryWrapper = new LambdaQueryWrapper<NavEntity>().eq(NavEntity::getTeamId, teamId).in(NavEntity::getSpaceId, spaceIds).eq(NavEntity::getDataType, DataTypeEnum.WEBSITE.getDataType()).eq(NavEntity::getDelFlag, DelFlagEnum.ENABLE.getCode());

        queryWrapper.and(StringUtils.isNotBlank(search), q -> q.like(StringUtils.isNotBlank(search), NavEntity::getName, search).or().like(StringUtils.isNotBlank(search), NavEntity::getDesc, search));

        Page<NavEntity> page = this.page(new Page<>(pageNo, pageSize), queryWrapper);

        List<NavEntity> entityList = page.getRecords();
        long total = page.getTotal();

        if (CollectionUtils.isEmpty(entityList)) {
            return TableDataInfo.build(total);
        }

        NavDatasDomain navDatasDomain = commonService.convertFromNavList(entityList, UserContext.getUid(), teamId);

        return new TableDataInfo<>(navDatasDomain.getFiles(), total);
    }

    @Override
    public WebsiteDetailDomain getWebsiteDetail(String publishId, String id) {
        teamPublishInfoService.checkNeedLogin(publishId);

        NavEntity navEntity = this.getById(id);
        if (null == navEntity) {
            throw new BusinessException(StatusCode.NO_RESOURCE, "未找到资源");
        }
        Integer dataType = navEntity.getDataType();

        DataTypeEnum dataTypeEnum = DataTypeEnum.of(dataType);
        if (!Objects.equals(dataTypeEnum, DataTypeEnum.WEBSITE)) {
            throw new BusinessException(StatusCode.NO_RESOURCE, "未找到资源");
        }

        NavWebsiteEntity websiteEntity = navWebsiteService.getByNavId(id);
        if (null == websiteEntity) {
            throw new BusinessException(StatusCode.NO_RESOURCE, "未找到资源");
        }

        WebsiteDetailDomain domain = new WebsiteDetailDomain();
        domain.setId(id);
        domain.setPid(navEntity.getPid());
        domain.setUrl(websiteEntity.getUrl());
        domain.setName(navEntity.getName());
        domain.setHitsNum(navEntity.getHitsNum());
        domain.setCollectNum(navEntity.getCollectNum());
        domain.setCreateTime(navEntity.getCreateTime());
        domain.setDesc(navEntity.getDesc());
        domain.setLogo(fileService.getFilePrefilePath(websiteEntity.getLogoKey()));
        domain.setBigDesc(websiteEntity.getDesc());

        if (!"root".equals(navEntity.getPid())) {
            NavEntity pNav = this.getById(navEntity.getPid());
            if (null != pNav) {
                domain.setCategoryName(pNav.getName());
            }
        } else {
            TeamSpaceEntity spaceEntity = teamSpaceService.getById(navEntity.getSpaceId());
            if (null != spaceEntity) {
                domain.setCategoryName(spaceEntity.getName());
            }
        }

        if (StringUtils.isNotBlank(navEntity.getTags())) {
            List<String> tags = Arrays.asList(navEntity.getTags().split(",")).stream().filter(tag -> StringUtils.isNotBlank(tag)).collect(Collectors.toList());
            domain.setTags(tags);
        }

        List<NavEntity> relationNavs = this.list(new LambdaQueryWrapper<NavEntity>().eq(NavEntity::getPid, navEntity.getPid()).eq(NavEntity::getSpaceId, navEntity.getSpaceId()).eq(NavEntity::getDataType, DataTypeEnum.WEBSITE.getDataType()).eq(NavEntity::getDelFlag, DelFlagEnum.ENABLE.getCode()).ne(NavEntity::getId, id));

        if (CollectionUtils.isNotEmpty(relationNavs) && relationNavs.size() > 16) {
            Collections.shuffle(relationNavs);
            relationNavs = relationNavs.subList(0, 16);
        }

        NavDatasDomain navDatasDomain = commonService.convertFromNavList(relationNavs, null, null);
        domain.setRelationWebsites(navDatasDomain.getFiles());

        return domain;
    }

    @Override
    public void increseHitsNum(String id) {
        NavEntity navEntity = this.getById(id);
        if (null != navEntity) {
            Integer oldHitsNum = Optional.ofNullable(navEntity.getHitsNum()).orElse(0);
            navEntity.setHitsNum(oldHitsNum + 1);
            this.updateById(navEntity);
        }
    }

    @Override
    public void increseCollectNum(String id) {
        NavEntity navEntity = this.getById(id);
        if (null != navEntity) {
            Integer oldCollectNum = Optional.ofNullable(navEntity.getCollectNum()).orElse(0);
            navEntity.setCollectNum(oldCollectNum + 1);
            this.updateById(navEntity);
        }
    }

    private void deleteFolder(String id) {
        final NavEntity userNav = this.getById(id);
        if (null == userNav) {
            return;
        }

        List<String> ids = Lists.newArrayList(id);
        List<String> fileKeys = new ArrayList<>();

        getDescendantIds(id, ids, fileKeys);

        this.removeBatchByIds(ids);
    }

    private void getDescendantIds(String pid, List<String> ids, List<String> fileKeys) {
        List<NavEntity> children = this.list(new LambdaQueryWrapper<NavEntity>().eq(NavEntity::getPid, pid));

        if (CollectionUtils.isEmpty(children)) {
            return;
        }

        for (NavEntity child : children) {
            ids.add(child.getId());

            final DataTypeEnum dataTypeEnum = DataTypeEnum.of(child.getDataType());
            switch (dataTypeEnum) {
                case DIR:
                    getDescendantIds(child.getId(), ids, fileKeys);
                    break;
                case WEBSITE:
                    NavWebsiteEntity website = navWebsiteService.getByNavId(child.getId());
                    if (null != website) {
                        fileKeys.add(website.getLogoKey());
                    }
                    break;
                case FILE:
                    // TODO: 2022/3/18 将文件的key放到fileKeys中
                    break;
            }
        }
    }

    /**
     * 删除网站
     *
     * @param id
     */
    private void deleteWebsite(String id) {
        final NavEntity userNav = getById(id);
        if (null == userNav) {
            return;
        }

        NavWebsiteEntity website = navWebsiteService.getByNavId(id);
        if (null != website) {
            navWebsiteService.removeById(website.getId());
        }

        this.removeById(id);

        navCommonUseService.remove(new LambdaQueryWrapper<NavCommonUseEntity>().eq(NavCommonUseEntity::getNavId, id));

        navOpenHistoryService.remove(new LambdaQueryWrapper<NavOpenHistoryEntity>().eq(NavOpenHistoryEntity::getNavId, id));
    }

    private void deleteFile(String id) {
        // TODO: 2022/3/18 删除文件

        navCommonUseService.remove(new LambdaQueryWrapper<NavCommonUseEntity>().eq(NavCommonUseEntity::getNavId, id));

        navOpenHistoryService.remove(new LambdaQueryWrapper<NavOpenHistoryEntity>().eq(NavOpenHistoryEntity::getNavId, id));
    }
}
