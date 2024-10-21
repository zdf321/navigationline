package net.xbookmark.api.nav.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.enums.DataTypeEnum;
import net.xbookmark.common.response.R;
import net.xbookmark.core.NavService;
import net.xbookmark.core.TeamPublishInfoService;
import net.xbookmark.dao.model.NavEntity;
import net.xbookmark.dao.model.TeamPublishInfoEntity;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangdingfei
 * @date 2023/12/19 17:11
 */
@RestController
@RequestMapping("/manual")
@Slf4j
public class ManualController {

  @Autowired private TeamPublishInfoService teamPublishInfoService;

  @Autowired private NavService navService;

  @ApiOperation("更新网站sitemap")
  @GetMapping("/updateSitemap")
  public R<Boolean> updateSitemap() {
    String sitemapFilePath = "/data/navigation/frontend/dist/sitemap.txt";

    File file = new File(sitemapFilePath);
    if (file.exists()) {
      file.delete();
    }

    List<TeamPublishInfoEntity> publishInfoEntities =
        teamPublishInfoService.list(
            new LambdaQueryWrapper<TeamPublishInfoEntity>()
                .eq(TeamPublishInfoEntity::getNeedLogin, 0)
                .eq(TeamPublishInfoEntity::getCheckAuth, 0)
                .eq(TeamPublishInfoEntity::getPublished, 1));

    for (TeamPublishInfoEntity publishInfoEntity : publishInfoEntities) {
      try {
        String publishUrl = "https://www.navigationline.cn/p/" + publishInfoEntity.getId() + "\n";
        FileUtils.write(file, publishUrl, "UTF-8", true);

        List<NavEntity> list =
            navService.list(
                new LambdaQueryWrapper<NavEntity>()
                    .eq(NavEntity::getTeamId, publishInfoEntity.getTeamId())
                    .eq(NavEntity::getDataType, DataTypeEnum.WEBSITE.getDataType()));

        for (NavEntity navEntity : list) {
          String url =
              "https://www.navigationline.cn/p/"
                  + publishInfoEntity.getId()
                  + "/"
                  + navEntity.getId()
                  + "\n";
          FileUtils.write(file, url, "UTF-8", true);
        }
      } catch (IOException e) {
        log.error("写sitemap文件异常", e);
      }
    }

    return R.success(true);
  }
}
