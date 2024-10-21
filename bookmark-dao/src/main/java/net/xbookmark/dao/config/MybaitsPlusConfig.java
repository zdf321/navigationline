package net.xbookmark.dao.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Author: zigui.zdf @Date: 2022/9/16 15:31:07 @Description:
 */
@Configuration
@MapperScan(basePackages = "net.xbookmark.dao.mapper")
public class MybaitsPlusConfig {

  @Autowired private DataSource dataSource;

  /** 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题 */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    return interceptor;
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(dataSource);
    sqlSessionFactory.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));
    sqlSessionFactory.setPlugins(mybatisPlusInterceptor());
    GlobalConfig globalConfig = new GlobalConfig();
    globalConfig.setBanner(false);
    globalConfig.setMetaObjectHandler(new CreateAndUpdateMetaObjectHandler());

    GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
    dbConfig.setIdType(IdType.ASSIGN_ID);
    dbConfig.setLogicDeleteValue("1");
    dbConfig.setLogicNotDeleteValue("0");
    globalConfig.setDbConfig(dbConfig);
    sqlSessionFactory.setGlobalConfig(globalConfig);
    return sqlSessionFactory.getObject();
  }
}
