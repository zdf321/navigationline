package net.xbookmark.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.xbookmark.api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

  @Autowired private TemplateEngine templateEngine;

  @Autowired private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.nickname}")
  private String nickname;

  @Override
  public void sendMail(String to, String subject, String content) {
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom(username);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(content);

    try {
      mailSender.send(message);
      log.info("简单邮件已经发送。");
    } catch (Exception e) {
      log.error("邮件发送失败：{}", e.getMessage());
    }
  }

  @Override
  public void sendMimeMail(String to, String subject, String content) {
    MimeMessage message = mailSender.createMimeMessage();

    String nick = new String(nickname);

    try {
      nick = MimeUtility.encodeText(nickname);
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(), e);
    }

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(new InternetAddress(nick + " <" + username + ">"));
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
      mailSender.send(message);
      log.info("邮件已经发送。");
    } catch (Exception e) {
      log.error("邮件发送失败：{}", e.getMessage());
    }
  }

  @Override
  public String buildEmailContentByTemplate(Map<String, Object> map, String templateName) {
    Context context = new Context();
    if (!CollectionUtils.isEmpty(map)) {
      context.setVariables(map);
    }

    StringWriter writer = new StringWriter();
    templateEngine.process(templateName, context, writer);

    return writer.toString();
  }

  public static void main(String[] args) {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("smtp.163.com");
    javaMailSender.setPort(465);
    javaMailSender.setUsername("18921121115@163.com");
    javaMailSender.setPassword("XXCHFSXWYREWYUQJ");
    Properties javaMailProperties = new Properties();
    javaMailProperties.put("mail.smtp.auth", true);
    javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    javaMailProperties.put("mail.smtp.socketFactory.port", 465);
    javaMailSender.setJavaMailProperties(javaMailProperties);

    MimeMessage message = javaMailSender.createMimeMessage();
    String nick = "";
    try {
      nick = MimeUtility.encodeText("虎符");
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(), e);
    }
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(new InternetAddress(nick + " <18921121115@163.com>"));
      helper.setTo("1069355234@qq.com");
      helper.setSubject("主题");
      helper.setText("这是测试", false);
      javaMailSender.send(message);
      System.out.println("邮件发送成功");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
