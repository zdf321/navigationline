package net.xbookmark.common.util;

import cn.hutool.core.util.IdUtil;
import com.qiniu.util.Base64;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.StatusCode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2020/12/5 21:48
 */
@Slf4j
public class ImgUtil {

  /**
   * 将本地的图片转为base64
   *
   * @param imgUrl
   * @return
   */
  public static String encodeLocalImg(String imgUrl) {
    Objects.requireNonNull(imgUrl);

    String fileSuffix = imgUrl.substring(imgUrl.lastIndexOf(".") + 1);

    byte[] data;
    try (InputStream inputStream = FileUtils.openInputStream(new File(imgUrl)); ) {
      data = new byte[inputStream.available()];
      inputStream.read(data);

      String base64 = Base64.encodeToString(data, Base64.NO_WRAP);

      // 加密
      return String.format("data:image/%s;base64,%s", fileSuffix, base64);
    } catch (IOException e) {
      log.error("读文件流异常", e);
      return null;
    }
  }

  /**
   * 将网络图片转为base64
   *
   * @param imgUrl
   * @return
   */
  public static String encodeNetImg(String imgUrl) {
    Objects.requireNonNull(imgUrl);

    OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS).build();
    final Request request = new Request.Builder().url(imgUrl).build();

    try (final Response response = httpClient.newCall(request).execute()) {
      String base64 = Base64.encodeToString(response.body().bytes(), Base64.NO_WRAP);

      // 加密
      return String.format("data:image/%s;base64,%s", "ico", base64);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      httpClient.connectionPool().evictAll();
    }

    return null;
  }

  /**
   * 将base64转为本地文件
   *
   * @param base64
   * @return
   */
  public static File decodeImg(String base64) {
    Objects.requireNonNull(base64);

    if (!base64.startsWith("data:image/")) {
      throw new BusinessException(StatusCode.FAILED, "参数错误");
    }

    File tempDir = FileUtils.getTempDirectory();
    if (!tempDir.exists()) {
      tempDir.mkdirs();
    }

    String fileSuffix = base64.substring(base64.indexOf("/") + 1, base64.indexOf(";"));

    String fileName = String.format("%s.%s", IdUtil.fastUUID(), fileSuffix);

    File tempFile = new File(tempDir + "/" + fileName);

    base64 = base64.substring(base64.indexOf(",") + 1);

    byte[] bytes = Base64.decode(base64, Base64.NO_WRAP);

    try {
      FileUtils.writeByteArrayToFile(tempFile, bytes);
      return tempFile;
    } catch (IOException e) {
      log.error("base64转图片文件异常", e);
    }
    return null;
  }

  public static void main(String[] args) {

    String url = "https://www.taobao.com/favicon.ico";

    final String s = encodeNetImg(url);
    System.out.println(s);
  }
}
