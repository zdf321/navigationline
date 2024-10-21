package net.xbookmark.dao.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.util.UserContext;
import net.xbookmark.dao.model.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * MP注入处理器:元对象字段填充控制器
 *
 * @author Lion Li
 * @date 2021/4/25
 */
@Slf4j
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    try {
      if (ObjectUtil.isNotNull(metaObject)
          && metaObject.getOriginalObject() instanceof BaseEntity) {
        BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
        Date current =
            ObjectUtil.isNotNull(baseEntity.getCreateTime())
                ? baseEntity.getCreateTime()
                : new Date();
        baseEntity.setCreateTime(current);
        baseEntity.setUpdateTime(current);
        String username =
            StringUtils.isNotBlank(baseEntity.getCreatorId())
                ? baseEntity.getCreatorId()
                : getLoginUsername();
        // 当前已登录 且 创建人为空 则填充
        baseEntity.setCreatorId(username);
        // 当前已登录 且 更新人为空 则填充
        baseEntity.setUpdaterId(username);
      }
    } catch (Exception e) {
      throw new BusinessException(
          String.valueOf(HttpStatus.HTTP_UNAUTHORIZED), "自动注入异常 => " + e.getMessage());
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    try {
      if (ObjectUtil.isNotNull(metaObject)
          && metaObject.getOriginalObject() instanceof BaseEntity) {
        BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
        Date current = new Date();
        // 更新时间填充(不管为不为空)
        baseEntity.setUpdateTime(current);
        String username = getLoginUsername();
        // 当前已登录 更新人填充(不管为不为空)
        if (StringUtils.isNotBlank(username)) {
          baseEntity.setUpdaterId(username);
        }
      }
    } catch (Exception e) {
      throw new BusinessException(
          String.valueOf(HttpStatus.HTTP_UNAUTHORIZED), "自动注入异常 => " + e.getMessage());
    }
  }

  /** 获取登录用户名 */
  private String getLoginUsername() {
    return UserContext.getUid();
  }
}
