package net.xbookmark.core;

import com.baomidou.mybatisplus.extension.service.IService;
import net.xbookmark.dao.model.NavWebsiteEntity;

import java.util.Collection;
import java.util.List;

/**
 * @author DELL
 * @description 针对表【tb_nav_website(网站信息表)】的数据库操作Service
 * @createDate 2023-07-29 17:04:43
 */
public interface NavWebsiteService extends IService<NavWebsiteEntity> {

  NavWebsiteEntity getByNavId(String navId);

  List<NavWebsiteEntity> getByNavIdList(Collection<String> navIdList);
}
