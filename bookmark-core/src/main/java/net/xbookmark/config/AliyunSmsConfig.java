//package net.xbookmark.config;
//
//import com.aliyun.teaopenapi.models.Config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///** @Author: zigui.zdf @Date: 2022/3/22 21:53:08 @Description: */
//@Configuration
//@ConditionalOnProperty(prefix = "nav.aliyun-sms", name = "ak")
//public class AliyunSmsConfig {
//
//  @Autowired private NavProperties navProperties;
//
//  @Bean
//  public com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
//    final String ak = navProperties.getAliyunSms().getAk();
//    final String sk = navProperties.getAliyunSms().getSk();
//    final String endpoint = navProperties.getAliyunSms().getEndpoint();
//
//    Config config =
//        new Config()
//            // 您的AccessKey ID
//            .setAccessKeyId(ak)
//            // 您的AccessKey Secret
//            .setAccessKeySecret(sk)
//            .setEndpoint(endpoint);
//
//    return new com.aliyun.dysmsapi20170525.Client(config);
//  }
//}
