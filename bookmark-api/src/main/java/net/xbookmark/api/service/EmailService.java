package net.xbookmark.api.service;

import java.util.Map;

public interface EmailService {

  void sendMail(String to, String subject, String content);

  String buildEmailContentByTemplate(Map<String, Object> map, String templateName);

  void sendMimeMail(String to, String subject, String content);
}
