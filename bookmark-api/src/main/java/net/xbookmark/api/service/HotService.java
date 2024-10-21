package net.xbookmark.api.service;

import net.xbookmark.api.nav.web.response.HotWebsiteResponse;

import java.util.List;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/12/13 21:45
 */
public interface HotService {

  List<String> keywords();

  List<HotWebsiteResponse> getHotWebsites();
}
