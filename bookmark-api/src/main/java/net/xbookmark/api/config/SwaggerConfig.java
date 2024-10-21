package net.xbookmark.api.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.config.NavProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/** @Author: zigui.zdf @Date: 2022/1/5 18:28:01 @Description: */
@EnableOpenApi
@EnableKnife4j
@Configuration
@Slf4j
public class SwaggerConfig implements WebMvcConfigurer {

  @Autowired private NavProperties navProperties;

  @Bean
  public Docket createRestApiForAll() {
    return new Docket(DocumentationType.OAS_30)
        // 定义是否开启swagger，false为关闭，可以通过变量控制
        .enable(navProperties.getSwagger().getOpen())
        .pathMapping("/")
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.any())
        .apis(RequestHandlerSelectors.basePackage("net.xbookmark.api.nav.web.controller"))
        .paths(PathSelectors.any())
        .build()
        .groupName("全部");
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("navigationline Api文档")
        .description("navigationline API接口")
        .version(navProperties.getSwagger().getApiVersion())
        .contact(new Contact("zigui", "http://www.navigationline.cn", "1069355234@qq.com"))
        .build();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    /** 配置knife4j 显示文档 */
    registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
  }
}
