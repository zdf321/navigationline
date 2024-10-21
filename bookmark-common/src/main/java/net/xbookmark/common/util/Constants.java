package net.xbookmark.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/10/31 23:15
 */
@Component
public class Constants {

    public static final String SESSION_USER_KEY = "user";

    public static final String JWT_SECRET_KEY = "SA4xb5mqd52dRyNT6a2NquHCPRZFDOk8";

    public static final String JWT_SUBJECT_NAV = "nav";

    public static String DOMAIN = "";

    @Value("${web.domain:localhost}")
    public void setDomain(String domain) {
        DOMAIN = domain;
    }

    public static final String JWT_TOKEN = "x-access-token";

    /**
     * token7天过期
     */
    public static final Integer JWT_TOKEN_TIMEOUT = 24 * 7;

    public static final String KAPTCHA_CODE = "kaptcha_code_key";

    /**
     * 验证码过期时间：15分钟
     */
    public static final int KAPTCHA_TIMEOUT = 15;

    public static class RedisKey {
        /**
         * 热门网站id
         */
        public static final String HOT_WEBSITE_KEY = "hot_website_ids";

        /**
         * 验证码前缀
         */
        public static final String KAPTCHA_PREFIX = "kaptcha:%s";

        /**
         * 短信验证码前缀
         */
        public static final String SMS_CODE_PREFIX = "sms:code:%s";

        public static final String USER_FILE_COUNT_PREFIX = "user_file_count:%s";

        public static final String OAUTH_STATE = "oauth_state:%s";
    }

    public static class FileKey {
        /**
         * oss上私有文件过期时间，单位：秒
         */
        public static final Integer OSS_PRIVATE_FILE_EXPIRE_TIME = 60 * 60 * 24;

        public static final String USER_WEBSITE_LOGO_PREFIX = "%s/image/site/logo";

        public static final String USER_WEBSITE_CONTENT_PREFIX = "%s/image/site/content";

        public static final String TEAM_SPACE_LOGO_PREFIX = "%s/space/logo";

        public static final String AVATAR_PREFIX = "user/%s/avatar/%s";

        /**
         * 文件预览前缀
         */
        public static final String FILE_PREVIEW_PATH = "/filePreview";
    }
}
