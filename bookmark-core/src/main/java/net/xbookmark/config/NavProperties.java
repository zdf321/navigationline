package net.xbookmark.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: zigui.zdf @Date: 2022/3/12 21:32:32 @Description:
 */
@Component
@ConfigurationProperties(prefix = "nav")
@Data
public class NavProperties {

    private final Swagger swagger = new Swagger();

    private final System system = new System();

    @Data
    public static class Swagger {
        private Boolean open = false;

        private String apiVersion = "v1";
    }

    @Data
    public static class System {
        /**
         * 非会员，最大文件数量
         */
        private Integer fileMaxCountVisitor = 100000000;

        /**
         * 团队版试用天数
         */
        private Integer teamTrialDays = 9999999;

        /**
         * 团队版试用团队中的人数
         */
        private Integer teamTrialPeople = 99999;

        /**
         * 文件存储路径
         */
        private String dirPath = "/data/navigation/resources";
    }
}
