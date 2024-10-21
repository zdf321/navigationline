package net.xbookmark.api.filter;

import com.google.common.collect.Lists;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/11/3 21:34
 */
@Configuration
public class CustomFilterConfig {

  @Bean
  public FilterRegistrationBean loginFilterRegistrationBean() {
    LoginFilter loginFilter = new LoginFilter();

    List<String> anonUrls =
        Lists.newArrayList(
            "/swagger**/**",
            "/v3/api-docs",
            "/webjars/springfox-swagger-ui/**",
            "/doc.html",
            "/webjars/js/**",
            "/webjars/css/**",
            "/kaptcha/**",
            "/user/login",
            "/user/register",
            "/oauth/getLoginUrl",
            "/oauth/qq/callback",
            "/oauth/weChat/callback",
            "/oauth/loginWithSession",
            "/p/getSearchCategories",
            "/nav/loadCategory",
            "/nav/loadNav",
            "/nav/searchNav",
            "/team/nav/getWebsiteDetail",
            "/nav/getFileDetail",
            "/nav/hit",
            "/captcha/**",
            "/tool/loadCategory",
            "/tool/loadTool",
            "/tool/searchTool",
            "/team/getTeamInfoByInviteId",
            "/team/getTeamPublishDatas",
            "/team/getPublishDatasByFolderId",
            "/team/searchTeamPublishDatasPage",
            "/manual/**");

    loginFilter.setAnonPath(anonUrls);

    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(loginFilter);
    registrationBean.addUrlPatterns("/*");
    registrationBean.setName("LoginFilter");
    registrationBean.setOrder(1);
    return registrationBean;
  }
}
