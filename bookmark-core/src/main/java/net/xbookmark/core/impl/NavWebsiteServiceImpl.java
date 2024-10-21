package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.core.NavWebsiteService;
import net.xbookmark.dao.mapper.NavWebsiteEntityMapper;
import net.xbookmark.dao.model.NavWebsiteEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author DELL
 * @description 针对表【tb_nav_website(网站信息表)】的数据库操作Service实现
 * @createDate 2023-07-29 17:04:43
 */
@Service
public class NavWebsiteServiceImpl extends ServiceImpl<NavWebsiteEntityMapper, NavWebsiteEntity>
    implements NavWebsiteService {

  @Override
  public NavWebsiteEntity getByNavId(String navId) {
    NavWebsiteEntity website =
        this.getOne(
            new LambdaQueryWrapper<NavWebsiteEntity>()
                .eq(NavWebsiteEntity::getNavId, navId)
                .last("limit 1"));

    return website;
  }

  @Override
  public List<NavWebsiteEntity> getByNavIdList(Collection<String> navIdList) {
    List<NavWebsiteEntity> list =
        this.list(
            new LambdaQueryWrapper<NavWebsiteEntity>().in(NavWebsiteEntity::getNavId, navIdList));

    return list;
  }
}
