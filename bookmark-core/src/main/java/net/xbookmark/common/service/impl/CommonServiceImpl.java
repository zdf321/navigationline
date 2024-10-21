package net.xbookmark.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.xbookmark.common.enums.DataTypeEnum;
import net.xbookmark.common.service.CommonService;
import net.xbookmark.core.FileService;
import net.xbookmark.core.NavCommonUseService;
import net.xbookmark.core.NavWebsiteService;
import net.xbookmark.core.domain.NavDataItemDomain;
import net.xbookmark.core.domain.NavDatasDomain;
import net.xbookmark.dao.model.NavCommonUseEntity;
import net.xbookmark.dao.model.NavEntity;
import net.xbookmark.dao.model.NavWebsiteEntity;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangdingfei
 * @date 2023/7/30 15:15
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private NavWebsiteService navWebsiteService;

    @Autowired
    private FileService fileService;

    @Autowired
    private NavCommonUseService navCommonUseService;

    @Override
    public NavDatasDomain convertFromNavList(List<NavEntity> navList, String uid, String teamId) {
        NavDatasDomain response = new NavDatasDomain();

        if (CollectionUtils.isEmpty(navList)) {
            return response;
        }

        List<String> websiteNavIdList =
                navList.stream()
                        .filter(n -> Objects.equals(n.getDataType(), DataTypeEnum.WEBSITE.getDataType()))
                        .map(NavEntity::getId)
                        .collect(Collectors.toList());

        Map<String, NavWebsiteEntity> websiteMap = new HashMap<>();
        List<String> inCommonUseNavIds = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(websiteNavIdList)) {
            List<NavWebsiteEntity> websiteEntityList = navWebsiteService.getByNavIdList(websiteNavIdList);
            websiteMap = websiteEntityList.stream().collect(Collectors.toMap(x -> x.getNavId(), x -> x));

            if (null != uid && null != teamId) {
                List<NavCommonUseEntity> commonUseEntityList =
                        navCommonUseService.list(
                                new LambdaQueryWrapper<NavCommonUseEntity>()
                                        .select(NavCommonUseEntity::getNavId)
                                        .eq(NavCommonUseEntity::getUid, uid)
                                        .eq(NavCommonUseEntity::getTeamId, teamId)
                                        .in(NavCommonUseEntity::getNavId, websiteNavIdList));
                inCommonUseNavIds =
                        commonUseEntityList.stream()
                                .map(NavCommonUseEntity::getNavId)
                                .collect(Collectors.toList());
            }
        }

        List<NavDataItemDomain> folders = new ArrayList<>();
        List<NavDataItemDomain> files = new ArrayList<>();

        for (NavEntity entity : navList) {
            // pid特殊处理下
            String pid = "root".equals(entity.getPid()) ? "root:" + entity.getSpaceId() : entity.getPid();

            NavDataItemDomain dataItem = new NavDataItemDomain();
            dataItem.setId(entity.getId());
            dataItem.setPid(pid);
            dataItem.setDataType(entity.getDataType());
            dataItem.setName(entity.getName());
            dataItem.setDesc(entity.getDesc());
            dataItem.setUpdateTime(entity.getUpdateTime());
            dataItem.setOwner("拥有者");
            dataItem.setSortNum(entity.getSort());

            final DataTypeEnum dataTypeEnum = DataTypeEnum.of(entity.getDataType());
            switch (dataTypeEnum) {
                case WEBSITE:
                    NavWebsiteEntity website = websiteMap.get(entity.getId());
                    if (null != website) {
                        dataItem.setLogo(fileService.getFilePrefilePath(website.getLogoKey()));
                        dataItem.setUrl(website.getUrl());
                    }
                    dataItem.setInCommonUse(inCommonUseNavIds.contains(entity.getId()));
                    files.add(dataItem);
                    break;
                case DIR:
                    folders.add(dataItem);
                    break;
            }
        }

        response.setFiles(files);
        response.setFolders(folders);

        return response;
    }
}
